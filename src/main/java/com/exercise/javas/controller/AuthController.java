package com.exercise.javas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/authloginfail")
    public String authloginfail() {
        return "authloginfail";
    }

    @GetMapping("/authfail")
    public String authfail() {
        return "authfail";
    }
}
