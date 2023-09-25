package dev.traydr.simplechat.core;

import java.security.SecureRandom;

public enum SecureRandomEnum {
    INSTANCE;
    private final SecureRandom random;

    SecureRandomEnum() {
        random = new SecureRandom();
    }
    
    public SecureRandom getRandom() {
        return random;
    }

}
