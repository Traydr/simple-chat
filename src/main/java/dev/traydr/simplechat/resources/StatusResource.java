package dev.traydr.simplechat.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusResource {
    @GetMapping("/server")
    public String serverStatus() {
        return "Still here!";
    }

    @GetMapping("/db")
    public String databaseStatus() {
        return "Not implemeted";
    }
}
