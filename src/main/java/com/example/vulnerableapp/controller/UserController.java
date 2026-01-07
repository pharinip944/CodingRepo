package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // FIX: Validate user name is not null or empty
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // FIX: Prevent XSS by rejecting names with HTML/script tags
        if (user.getName().matches(".*[<>\"'&].*")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // FIX: Validate email format
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
