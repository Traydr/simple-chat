package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.exceptions.ResourceNotFoundException;
import dev.traydr.simplechat.repositories.GroupRepository;
import dev.traydr.simplechat.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    private final GroupRepository groupRepo;
    private final TokenRepository tokenRepo;

    public GroupController(GroupRepository groupRepo, TokenRepository tokenRepo) {
        this.groupRepo = groupRepo;
        this.tokenRepo = tokenRepo;
    }

    @GetMapping("")
    public List<Group> getGroups() {
        return groupRepo.findAll();
    }

    @GetMapping("/without_joined")
    public List<Group> getGroupsNotJoined(@CookieValue("token") String token) {
        return groupRepo.getGroupsByJoinedUsersNotContains(tokenRepo.findByToken(token).getUser());
    }

    @GetMapping("single")
    public Group getSpecificGroup(@RequestParam("gid") long groupId) {
        return groupRepo.findById(groupId).orElseThrow();
    }

    @Transactional
    @PostMapping("")
    public Group createGroup(@CookieValue("token") String token, @RequestParam("name") String name) {
        Group group = new Group();
        group.setName(Jsoup.clean(name, Safelist.none()));

        User owner = tokenRepo.findByToken(token).getUser();

        group.setOwner(owner);

        group.addUser(owner);
        owner.addGroup(group);
        return groupRepo.saveAndFlush(group);
    }

    @GetMapping("/joined")
    public List<Group> joinedGroups(@CookieValue("token") String token) {
        User requester = tokenRepo.findByToken(token).getUser();
        return requester.getJoinedGroups();
    }

    @Transactional
    @PutMapping("join")
    public Group joinGroup(@CookieValue("token") String token, @RequestParam("groupId") long groupId) throws Exception {
        User requester = tokenRepo.findByToken(token).getUser();
        Group group = groupRepo.findById(groupId).orElseThrow();

        if (group.getJoinedUsers().contains(requester)) {
            throw new Exception("User already in group");
        }

        group.addUser(requester);
        requester.addGroup(group);

        groupRepo.saveAndFlush(group);

        return group;
    }

    @Transactional
    @PutMapping("leave")
    public Group leaveGroup(@CookieValue("token") String token, @RequestParam("groupId") long groupId) throws Exception {
        User requester = tokenRepo.findByToken(token).getUser();
        Group group = groupRepo.findById(groupId).orElseThrow();

        if (!group.getJoinedUsers().contains(requester)) {
            throw new Exception("User not in group");
        }

        group.removeUser(requester);
        requester.removeGroup(group);

        groupRepo.saveAndFlush(group);

        return group;
    }

    @DeleteMapping("")
    public boolean deleteGroup(@CookieValue("token") String token, @RequestParam("gid") long groupId) {
        User requester = tokenRepo.findByToken(token).getUser();
        Group group = groupRepo.findById(groupId).orElseThrow();

        if (group.getOwner().equals(requester)) {


            groupRepo.delete(group);
            return true;
        } else {
            return false;
        }
    }
}
