package com.example.vulnerableapp.controller;
import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {
private final UserService service;
public UserController(UserService service){this.service=service;}
@GetMapping("/search")
public List<User> search(@RequestParam String username){
return service.searchUser(username);
}
}