package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
    private final UserRepository userRepo;

    public StatusController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/server")
    public String serverStatus() {
        return "Server Up";
    }

    @GetMapping("/db")
    public String databaseStatus() {
        userRepo.findAll();
        return "Database Connected";
    }
}
