package dev.traydr.simplechat.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Date expires;

    public Token() {
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
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
