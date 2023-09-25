package dev.traydr.simplechat.services;

import dev.traydr.simplechat.entities.User;
import dev.traydr.simplechat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(int id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
