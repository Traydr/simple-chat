package dev.traydr.simplechat.repositories;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    public List<Message> findByGroup(Group group);
}
