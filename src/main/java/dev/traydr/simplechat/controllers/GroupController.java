package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    private GroupRepository groupRepo;
    @GetMapping("")
    public List<Group> getGroups() {
        return groupRepo.findAll();
    }
}
