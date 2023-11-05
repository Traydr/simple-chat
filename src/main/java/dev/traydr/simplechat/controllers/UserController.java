package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.core.CurrentDate;
import dev.traydr.simplechat.core.Password;
import dev.traydr.simplechat.entities.Login;
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
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long uid) throws ResourceNotFoundException {
        User user = userRepo.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + uid));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userRepo.findByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody Login login) {
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(Password.hashPassword(login.getPassword()));
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
