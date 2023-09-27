package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.exceptions.ResourceNotFoundException;
import dev.traydr.simplechat.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long uid) throws ResourceNotFoundException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + uid));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userRepository.getUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long uid,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + uid));

        // TODO: revamp this
        user.setUid(userDetails.getUid());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setCreatedAt(userDetails.getCreatedAt());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long uid)
            throws ResourceNotFoundException {
        User User = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("uid not found: " + uid));

        userRepository.delete(User);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
