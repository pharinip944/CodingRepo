package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// FIX: Import BCryptPasswordEncoder for password hashing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// FIX: Import Pattern for input validation
import java.util.regex.Pattern;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // FIX: Add BCryptPasswordEncoder instance for password hashing
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // FIX: Add regex pattern for allowed username and password characters
    private static final Pattern VALID_INPUT_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,32}$");

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // FIX: Basic input validation for username and password
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be null.");
        }
        if (!VALID_INPUT_PATTERN.matcher(user.getUsername()).matches()) {
            throw new IllegalArgumentException("Username must be 4-32 characters and contain only letters, numbers, and underscores.");
        }
        if (!VALID_INPUT_PATTERN.matcher(user.getPassword()).matches()) {
            throw new IllegalArgumentException("Password must be 4-32 characters and contain only letters, numbers, and underscores.");
        }
        // FIX: Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
