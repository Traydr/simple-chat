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
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long uid) throws ResourceNotFoundException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + uid));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long uid,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + uid));

        user.setUid(userDetails.getUid());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setSalt(userDetails.getSalt());
        user.setCreatedAt(userDetails.getCreatedAt());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
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
