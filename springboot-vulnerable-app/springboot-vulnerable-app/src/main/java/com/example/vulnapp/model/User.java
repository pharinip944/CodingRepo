package com.example.vulnapp.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class User {
    private Long id;
    private String username;
    private String passwordHash;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        setPassword(password);
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
