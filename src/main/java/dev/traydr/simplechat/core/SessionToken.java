package dev.traydr.simplechat.core;

import dev.traydr.simplechat.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SessionToken {
    @Autowired
    private TokenRepository tokenRepo;

    public String createToken() {
        while (true) {
            Base64.Encoder encoder = Base64.getUrlEncoder();
            byte[] randomBytes = new byte[30];
            SecureRandomEnum.INSTANCE.getRandom().nextBytes(randomBytes);
            String newToken = encoder.encodeToString(randomBytes);

            // Check if token collides
            if (tokenRepo.ifExistsByToken(newToken) != 1) {
                return newToken;
            }
        }
    }
}
