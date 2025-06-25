package com.luis.multiportal.controllers;

import com.luis.multiportal.dto.PersonsDTO;
import com.luis.multiportal.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ViewController {
    @Autowired
    private CsvService csvService;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/upload")
    public String upload(Model model, Principal principal, @RequestParam(defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("usuario", auth.getName());
        }

        Pageable pageable = PageRequest.of(page, 5);
        Page<PersonsDTO> pessoas = csvService.buscarPaginado(pageable);

        model.addAttribute("usuarios", pessoas);
        return "upload";
    }

    @GetMapping("/criar")
    public String criar() {
        return "criar";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
