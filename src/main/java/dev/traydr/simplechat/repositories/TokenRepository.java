package dev.traydr.simplechat.repositories;

import dev.traydr.simplechat.entities.Token;
import dev.traydr.simplechat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    public boolean existsByToken(String token);
    public Token findByToken(String token);
    public Token findByUser(User user);
}
