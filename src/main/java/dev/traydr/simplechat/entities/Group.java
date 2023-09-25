package dev.traydr.simplechat.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "joinedGroups")
    private List<User> joinedUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Group(String name) {
        this.name = name;
    }

    public Group() {
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

    @Override
    public String toString() {
        return "Group [gid=" + gid + ", name=" + name + "]";
    }
}
