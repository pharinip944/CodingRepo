package com.example.vulnerableapp.model;

import javax.persistence.*;
// FIX: Prevent password exposure in API responses
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    // FIX: Prevent password exposure in API responses
    @JsonIgnore
    private String password;

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
