package com.erns.exam.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if ("ernest".equals(username) && "thethird".equals(password)) {
            return "redirect:/index";
        }
        // Optionally add an error message to the model here
        return "index";
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}

