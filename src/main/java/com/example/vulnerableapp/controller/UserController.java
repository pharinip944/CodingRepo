package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        // FIX: Do not return passwords in API responses
        return userService.getAllUsers().stream().map(user -> {
            User safeUser = new User();
            safeUser.setId(user.getId());
            safeUser.setUsername(user.getUsername());
            // FIX: Do NOT set password in response
            return safeUser;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // FIX: Input validation and password hashing are handled in the service
        User created = userService.createUser(user);
        // FIX: Do not return password in API response
        User safeUser = new User();
        safeUser.setId(created.getId());
        safeUser.setUsername(created.getUsername());
        // FIX: Do NOT set password in response
        return safeUser;
    }
}
