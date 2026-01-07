package com.example.vulnerableapp.model;

public class User {
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        // FIX: Sanitize output to prevent XSS
        return name != null ? name.replaceAll("[<>]", "") : null;
    }

    public void setName(String name) {
        // FIX: Sanitize input to prevent XSS
        this.name = name != null ? name.replaceAll("[<>]", "") : null;
    }

    public String getEmail() {
        // FIX: Sanitize output to prevent XSS
        return email != null ? email.replaceAll("[<>]", "") : null;
    }

    public void setEmail(String email) {
        // FIX: Sanitize input to prevent XSS
        this.email = email != null ? email.replaceAll("[<>]", "") : null;
    }
}
