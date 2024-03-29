package dev.traydr.simplechat.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gid", referencedColumnName = "gid")
    private Group group;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "sent_at", nullable = false)
    private Date sentAt;

    public Message(User user, Group group, String message) {
        this.user = user;
        this.group = group;
        this.message = message;
        this.sentAt = new Date(Instant.now().toEpochMilli());
    }

    public Message() {
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
