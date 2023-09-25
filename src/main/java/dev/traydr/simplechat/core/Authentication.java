package dev.traydr.simplechat.core;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.repositories.GroupRepository;
import dev.traydr.simplechat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authentication {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

     public boolean login(String username, String password) {
         // Todo: return token on valid credentials
         return false;
    }

    public boolean validToken(String token, long id) {
         return false;
    }
}
