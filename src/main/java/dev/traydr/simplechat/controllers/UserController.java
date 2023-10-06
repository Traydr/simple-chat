package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.core.CurrentDate;
import dev.traydr.simplechat.core.Password;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.exceptions.ResourceNotFoundException;
import dev.traydr.simplechat.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    /*
    TODO: Add authentication to each endpoint
        - Look into use @ tags to authenticate
    TODO: Do not return user passwords
    TODO: Return Token when registration complete
    TODO: User requires method to search by username to get id
     */

    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public List<User> getAllUsers() {
        List<User> users = userRepo.findAll();
        for (User user: users) {
            user.setPassword("");
        }
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long uid) throws ResourceNotFoundException {
        User user = userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + uid));
        user.setPassword("");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userRepo.findByUsername(username);
        user.setPassword("");
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(Password.hashPassword(user.getPassword()));
        user.setCreatedAt(CurrentDate.getCurrentDate(0));
        return userRepo.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long uid,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        // TODO: revamp this
        User user = userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + uid));

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());

        final User updatedUser = userRepo.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long uid, @CookieValue(value = "token") String token)
            throws ResourceNotFoundException {
        User User = userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("uid not found: " + uid));

        userRepo.delete(User);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
