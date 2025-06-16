package com.luis.multiportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/upload")
    public String upload() {
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
