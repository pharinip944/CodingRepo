package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // FIX: Validate username: must be alphanumeric and 3-20 characters
        if (user.getUsername() == null ||
            !user.getUsername().matches("^[a-zA-Z0-9]{3,20}$")) {
            throw new IllegalArgumentException("Username must be 3-20 alphanumeric characters.");
        }

        // FIX: Validate password: at least 8 chars, at least one letter and one number
        String password = user.getPassword();
        if (password == null ||
            password.length() < 8 ||
            !password.matches(".*[A-Za-z].*") ||
            !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters and include at least one letter and one number.");
        }

        // FIX: Hash password before storing (using SHA-256 for demonstration; use bcrypt in production)
        user.setPassword(hashPassword(password));
        return userRepository.save(user);
    }

    // FIX: Added password hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
}
