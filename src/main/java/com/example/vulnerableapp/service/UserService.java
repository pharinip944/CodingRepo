package com.example.vulnerableapp.service;
import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
private final UserRepository repository;
public UserService(UserRepository repository){this.repository=repository;}
public List<User> searchUser(String username){
return repository.findUserByUsername(username);
}
}