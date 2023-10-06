package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.exceptions.ResourceNotFoundException;
import dev.traydr.simplechat.repositories.GroupRepository;
import dev.traydr.simplechat.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private TokenRepository tokenRepo;

    @GetMapping("")
    public List<Group> getGroups() {
        return groupRepo.findAll();
    }

    @PostMapping("")
    public Group createGroup(@CookieValue("token") String token, @RequestParam("name") String name) {
        Group group = new Group();
        group.setName(name);

        User owner = tokenRepo.findByToken(token).getUser();

        group.setOwner(owner);
        List<User> joinedUsers = group.getJoinedUsers();
        joinedUsers.add(owner);
        return groupRepo.save(group);
    }

    @GetMapping("/joined")
    public List<Group> joinedGroups(@CookieValue("token") String token) {
        User requester = tokenRepo.findByToken(token).getUser();
        return requester.getJoinedGroups();
    }

    @PutMapping("join")
    public Group joinGroup(@CookieValue("token") String token, @RequestParam("groupId") long groupId) throws ResourceNotFoundException {
        User requester = tokenRepo.findByToken(token).getUser();
        Optional<Group> group = groupRepo.findById(groupId);

        if (group.isEmpty()) {
            throw new ResourceNotFoundException("Group id not found");
        }

        Group toJoin = groupRepo.findById(groupId).get();
        List<User> users = toJoin.getJoinedUsers();
        users.add(requester);
        toJoin.setJoinedUsers(users);
        return groupRepo.save(toJoin);
    }
 }
