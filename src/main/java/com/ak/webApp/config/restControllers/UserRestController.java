package com.ak.webApp.config.restControllers;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import com.ak.webApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {
    private UserRepository userRepository;
    private UserService userService;


    @Autowired
    public UserRestController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/rest/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/rest/users/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userRepository.findByUserId(id);
    }
}
