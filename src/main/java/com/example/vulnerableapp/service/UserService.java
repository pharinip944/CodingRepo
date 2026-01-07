package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // FIX: Added input validation to prevent XSS and invalid data
        if (user.getName() == null || !Pattern.matches("^[a-zA-Z0-9_\- ]{1,50}$", user.getName())) {
            // FIX: Name must be alphanumeric, dash, underscore, space, 1-50 chars
            throw new IllegalArgumentException("Invalid user name");
        }
        // FIX: Added email validation to prevent XSS and invalid emails
        if (user.getEmail() == null || !Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        return userRepository.save(user);
    }
}
