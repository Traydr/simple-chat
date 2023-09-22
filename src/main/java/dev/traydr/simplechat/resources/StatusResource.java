package dev.traydr.simplechat.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {
    @GetMapping("/status/server")
    public String serverStatus() {
        return "Still here!";
    }

    @GetMapping("/status/db")
    public String databaseStatus() {
        return "Not implemeted";
    }
}
