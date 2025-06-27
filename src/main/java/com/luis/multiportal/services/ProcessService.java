package com.luis.multiportal.services;

import com.luis.multiportal.dto.ProcessDTO;
import com.luis.multiportal.exceptions.UserCreationException;
import com.luis.multiportal.models.Persons;
import com.luis.multiportal.models.Process;
import com.luis.multiportal.models.User;
import com.luis.multiportal.models.enuns.RoleEnum;
import com.luis.multiportal.repositoreis.ProcessRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;

    @Transactional
    public Process create(Process obj, MultipartFile file, List<Persons> persons, Map<String, Long> contagemPorSexo, Map<String, Double> mediaIdadePorSexo) {

        obj.setId(null);
        obj.setFileName(file.getName());
        obj.setTotalRecords(persons.size());
        obj.setFemaleCount(contagemPorSexo.get("Female").intValue());
        obj.setMaleCount(contagemPorSexo.get("Male").intValue());
        obj.setAverageAgeFemale(mediaIdadePorSexo.getOrDefault("Female", 0.0));
        obj.setAverageAgeMale(mediaIdadePorSexo.getOrDefault("Male", 0.0));
        obj.setProcessedAt(null);
        return processRepository.save(obj);
    }

    public Page<ProcessDTO> getAllOrderedByFileName(Pageable pageable) {
        return processRepository
                .findAllByOrderByFileNameAsc(pageable)
                .map(entity -> new ProcessDTO(
                        entity.getId(),
                        entity.getFileName(),
                        entity.getTotalRecords(),
                        entity.getFemaleCount(),
                        entity.getMaleCount(),
                        entity.getAverageAgeFemale(),
                        entity.getAverageAgeMale(),
                        entity.getProcessedAt()
                ));
    }



}
