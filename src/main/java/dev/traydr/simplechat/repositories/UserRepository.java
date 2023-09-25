package dev.traydr.simplechat.repositories;

import dev.traydr.simplechat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
