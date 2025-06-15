package com.luis.multiportal.services;
import com.luis.multiportal.models.Persons;
import com.luis.multiportal.repositoreis.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CsvService {

    @Autowired
    private PersonsRepository personsRepository;

    public List<Persons> parseCsv(MultipartFile file) throws IOException {
        List<Persons> personsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.replace("\"", "").split(",");

                if (values.length >= 7) {
                    Persons person = new Persons();
                    person.setNome(values[0]);
                    person.setSobreNome(values[1]);
                    person.setEmail(values[2]);
                    person.setSexo(values[3]);
                    person.setIpAcesso(values[4]);

                    try {
                        person.setIdade(Integer.parseInt(values[5]));
                        person.setNascimento(values[6]);
                        person.corrigirDataNascimento();
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    personsList.add(person);
                }
            }
        }

        personsList.sort(Comparator.comparing(Persons::getNome, String.CASE_INSENSITIVE_ORDER));

        return personsList;
    }

    public Map<String, Long> contarPorSexo() {
        return Map.of(
                "Male", personsRepository.countBySexo("Male"),
                "Female", personsRepository.countBySexo("Female")
        );
    }

    public Map<String, Double> calcularMediaIdadePorSexo() {
        List<Persons> allPersons = personsRepository.findAll();

        return allPersons.stream()
                .collect(Collectors.groupingBy(
                        Persons::getSexo,
                        Collectors.averagingInt(Persons::getIdade)
                ));
    }

    public void salvarTodos(List<Persons> persons) {
        personsRepository.saveAll(persons);
    }

    public List<Persons> listarTodos() {
        return personsRepository.findAll();
    }
}