package dev.traydr.simplechat.core;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Password {
    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean validPassword(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
