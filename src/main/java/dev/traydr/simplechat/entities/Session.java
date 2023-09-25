package dev.traydr.simplechat.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @OneToOne(mappedBy = "session")
    public User user;

    @Column(name = "token", nullable = false)
    public String token;

    @Column(name = "expires", nullable = false)
    public Date expires;

    public Session() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
