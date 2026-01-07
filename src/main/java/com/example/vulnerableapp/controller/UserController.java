package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        // FIX: Do not expose password in API response
        return userService.getAllUsers().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @PostMapping
    public UserResponse createUser(@RequestBody User user) {
        // FIX: Input validation for username and password to prevent injection and weak passwords
        if (user.getUsername() == null || !Pattern.matches("^[a-zA-Z0-9_]{3,50}$", user.getUsername())) {
            throw new IllegalArgumentException("Invalid username. Only alphanumeric and underscore, 3-50 chars.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8 || user.getPassword().length() > 100) {
            throw new IllegalArgumentException("Invalid password. Must be 8-100 chars.");
        }
        // FIX: Do not log or return password
        User created = userService.createUser(user);
        return new UserResponse(created);
    }

    // FIX: Response DTO to avoid exposing password
    public static class UserResponse {
        private Long id;
        private String username;
        public UserResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
        public Long getId() { return id; }
        public String getUsername() { return username; }
    }
}
