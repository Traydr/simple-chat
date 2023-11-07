package dev.traydr.simplechat.repositories;

import dev.traydr.simplechat.entities.Group;
import dev.traydr.simplechat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    public List<Group> getGroupsByJoinedUsersNotContains(User user);
}
