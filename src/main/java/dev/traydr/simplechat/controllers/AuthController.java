package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.core.CurrentDate;
import dev.traydr.simplechat.core.Password;
import dev.traydr.simplechat.core.SessionToken;
import dev.traydr.simplechat.entities.Token;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.repositories.TokenRepository;
import dev.traydr.simplechat.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TokenRepository tokenRepo;

    // 7 Days
    private static final int cookieMaxAge = 7 * 24 * 60 * 60;

    public ResponseEntity<User> Login(@Valid @RequestBody User attempt, HttpServletResponse response) {
        User user = userRepo.findByUsername(attempt.getUsername());
        if (!Password.validPassword(attempt.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Creating Token
        SessionToken sessionToken = new SessionToken();
        String stringToken = sessionToken.createToken();

        // Set Token
        Token token = new Token();
        token.setUser(user);
        token.setToken(stringToken);
        token.setExpires(CurrentDate.getCurrentDate(cookieMaxAge));
        tokenRepo.save(token);

        // Set Cookies
        Cookie tokenCookie = new Cookie("token", stringToken);
        Cookie uidCookie = new Cookie("uid", user.getUid().toString());

        tokenCookie.setMaxAge(cookieMaxAge);
        uidCookie.setMaxAge(cookieMaxAge);

        response.addCookie(tokenCookie);
        response.addCookie(uidCookie);

        // Return User
        user.setPassword("");
        return ResponseEntity.ok().body(user);
    }
}
