package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid; // FIX: Added for input validation
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody User user) {
        // FIX: Minimal input sanitization to mitigate XSS
        user.setName(user.getName() != null ? user.getName().replaceAll("[<>]", "") : null);
        user.setEmail(user.getEmail() != null ? user.getEmail().replaceAll("[<>]", "") : null);
        return userService.createUser(user);
    }
}
