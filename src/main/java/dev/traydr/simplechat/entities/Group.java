package dev.traydr.simplechat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "joinedGroups", cascade = CascadeType.ALL)
    private List<User> joinedUsers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    public List<User> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(List<User> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        if (this.joinedUsers == null) {
            this.joinedUsers = new ArrayList<>();
        }

        this.joinedUsers.add(user);
    }

    public void removeUser(User user) {
        this.joinedUsers.remove(user);
    }

    @PreRemove
    public void removeAllAssociations() {
        for (User user: this.joinedUsers) {
            user.removeGroup(this);
        }

        this.joinedUsers.clear();
        this.owner.removeGroup(this);
        this.owner = null;
    }

    @Override
    public String toString() {
        return "Group [gid=" + gid + ", name=" + name + "]";
    }
}
