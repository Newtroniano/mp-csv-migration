package com.luis.multiportal.controllers;



import com.luis.multiportal.models.Persons;
import com.luis.multiportal.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
@Validated
public class CsvController {

    @Autowired
    private CsvService csvService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            List<Persons> persons = csvService.parseCsv(file);
            csvService.salvarTodos(persons);

            Map<String, Long> contagemPorSexo = csvService.contarPorSexo();
            Map<String, Double> mediaIdadePorSexo = csvService.calcularMediaIdadePorSexo();

            return ResponseEntity.ok(Map.of(
                    "message", "Arquivo processado com sucesso",
                    "totalRegistros", persons.size(),
                    "contagemPorSexo", contagemPorSexo,
                    "mediaIdadePorSexo", mediaIdadePorSexo
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportarCsv() {
        List<Persons> persons = csvService.listarTodos();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("Nome,SobreNome,Email,Sexo,IpAcesso,Idade,Nascimento");

        for (Persons person : persons) {
            writer.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d,\"%s\"",
                    person.getNome(),
                    person.getSobreNome(),
                    person.getEmail(),
                    person.getSexo(),
                    person.getIpAcesso(),
                    person.getIdade(),
                    person.getNascimento()
            ));
        }

        writer.flush();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "dados_corrigidos.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

    @GetMapping
    public ResponseEntity<List<Persons>> listarTodos() {
        return ResponseEntity.ok(csvService.listarTodos());
    }
}