package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.sql.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        // FIX: Mask email before returning users to avoid PII exposure
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (user.getEmail() != null) {
                // FIX: Mask all but first and last character of email local part
                String[] parts = user.getEmail().split("@", 2);
                if (parts.length == 2 && parts[0].length() > 2) {
                    String masked = parts[0].charAt(0) + "***" + parts[0].charAt(parts[0].length() - 1) + "@" + parts[1];
                    user.setEmail(masked);
                } else {
                    user.setEmail("***@***");
                }
            }
        }
        return users;
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) throws SQLException {
        // FIX: Use parameterized query to prevent SQL Injection
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vulnerableapp", "root", System.getenv("DB_PASSWORD"));
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // FIX: Validate user input to prevent XSS and invalid data
        if (user.getName() == null || !Pattern.matches("^[a-zA-Z0-9_\- ]{1,50}$", user.getName())) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (user.getEmail() == null || !Pattern.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", user.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        return userService.createUser(user);
    }
}
