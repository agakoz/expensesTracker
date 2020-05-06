package com.ak.webApp.controller;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {
    private UserRepository userRepository;
private User user;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
        this.user= new User();
    }

    @GetMapping(path = "/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }



}
