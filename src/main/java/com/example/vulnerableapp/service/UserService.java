package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        // FIX: Remove password from returned users to avoid PII exposure
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPassword(null); // FIX: Mask password field
        }
        return users;
    }

    public User createUser(User user) {
        // FIX: Basic validation for username and password to prevent missing validation
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        // FIX: Remove password from returned user to avoid PII exposure
        User savedUser = userRepository.save(user);
        savedUser.setPassword(null); // FIX: Mask password field
        return savedUser;
    }
}
