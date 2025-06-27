package com.luis.multiportal.controllers;

import com.luis.multiportal.dto.ProcessDTO;
import com.luis.multiportal.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
@Validated
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @GetMapping("/processing-summary")
    public Page<ProcessDTO> getProcessingSummaries(Pageable pageable) {
        return processService.getAllOrderedByFileName(pageable);
    }
}
