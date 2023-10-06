package dev.traydr.simplechat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/groups")
    public String groups() {
        return "groups";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }
}
