package dev.traydr.simplechat.controllers;

import dev.traydr.simplechat.core.CurrentDate;
import dev.traydr.simplechat.core.Password;
import dev.traydr.simplechat.core.SecureRandomEnum;
import dev.traydr.simplechat.entities.Login;
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
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;

    // 7 Days
    private static final int cookieMaxAge = 7 * 24 * 60 * 60;

    public AuthController(UserRepository userRepo, TokenRepository tokenRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    @PostMapping("")
    public ResponseEntity<User> Login(@Valid @ModelAttribute Login attempt, HttpServletResponse response) {
        User user = userRepo.findByUsername(attempt.getUsername());
        if (!Password.validPassword(attempt.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Token existingToken = tokenRepo.findByUser(user);
        String stringToken;
        if (existingToken != null && existingToken.getExpires().after(CurrentDate.getCurrentDate(0))) {
            existingToken.setExpires(CurrentDate.getCurrentDate(cookieMaxAge));
            tokenRepo.saveAndFlush(existingToken);
            stringToken = existingToken.getToken();
        } else {
            // Creating Token
            stringToken = createToken();

            // Set Token
            Token token = new Token();
            if (existingToken != null) {
                token.setTid(existingToken.getTid());
            }
            token.setUser(user);
            token.setToken(stringToken);
            token.setExpires(CurrentDate.getCurrentDate(cookieMaxAge));
            tokenRepo.saveAndFlush(token);
        }

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

    public String createToken() {
        while (true) {
            Base64.Encoder encoder = Base64.getUrlEncoder();
            byte[] randomBytes = new byte[30];
            SecureRandomEnum.INSTANCE.getRandom().nextBytes(randomBytes);
            String newToken = encoder.encodeToString(randomBytes);

            if (!tokenRepo.existsByToken(newToken)) {
                return newToken;
            }
        }
    }
}
