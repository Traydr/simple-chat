package dev.traydr.simplechat.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public User user;

    @Column(name = "token", nullable = false)
    public String token;

    @Column(name = "expires_at", nullable = false)
    public Date expires;

    public Token() {
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
