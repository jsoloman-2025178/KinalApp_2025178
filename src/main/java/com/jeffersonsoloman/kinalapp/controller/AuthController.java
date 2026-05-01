package com.jeffersonsoloman.kinalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        System.out.println("=== Mostrando página de login ===");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        System.out.println("=== Mostrando dashboard ===");
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}