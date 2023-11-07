package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.core.CurrentDate;
import dev.traydr.simplechat.core.Password;
import dev.traydr.simplechat.entities.ChangeDetails;
import dev.traydr.simplechat.entities.Login;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.exceptions.ResourceNotFoundException;
import dev.traydr.simplechat.repositories.TokenRepository;
import dev.traydr.simplechat.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;

    public UserController(UserRepository userRepo, TokenRepository tokenRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    @GetMapping("/")
    public User getUserByToken(@CookieValue("token") String token) {
        return tokenRepo.findByToken(token).getUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long uid) {
        User user = userRepo.findById(uid).orElseThrow();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("profile/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) throws ResourceNotFoundException {
        User user = userRepo.findByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("")
    public User createUser(@Valid @ModelAttribute Login login) {
        User user = new User();
        user.setUsername(login.getUsername());
        user.setPassword(Password.hashPassword(login.getPassword()));
        user.setCreatedAt(CurrentDate.getCurrentDate(0));
        return userRepo.saveAndFlush(user);
    }

    @PutMapping("")
    public User updateUser(@CookieValue("token") String token,
                           @Valid @ModelAttribute ChangeDetails details) {
        User user = tokenRepo.findByToken(token).getUser();

        if (!Password.validPassword(details.getOldPassword(), user.getPassword())) {
            return null;
        }
        user.setUsername(details.getUsername());
        user.setPassword(details.getNewPassword());

        return userRepo.saveAndFlush(user);
    }

    @DeleteMapping("/")
    public Map<String, Boolean> deleteUser(@CookieValue(value = "token") String token) {
        User user = tokenRepo.findByToken(token).getUser();
        userRepo.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
