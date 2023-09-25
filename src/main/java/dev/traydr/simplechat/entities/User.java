package dev.traydr.simplechat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int uid;

    private String username;
    private String password;
    private String salt;
    private Date createdAt;

    public User(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }


    /**
     * Creates and empty user
     */
    public User() {
        uid = -1;
        username = "";
        password = "";
        salt = "";

        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        createdAt = new Date(zdt.toInstant().toEpochMilli());
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
