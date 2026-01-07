package com.example.vulnerableapp.model;

public class User {
    private Long id;
    private String username;
    // FIX: Mark password as transient to avoid accidental serialization/logging
    private transient String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
