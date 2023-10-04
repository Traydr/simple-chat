package dev.traydr.simplechat.repositories;

import dev.traydr.simplechat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u From User u where u.username = :username")
    public User findByUsername(@Param("username") String username);
}
