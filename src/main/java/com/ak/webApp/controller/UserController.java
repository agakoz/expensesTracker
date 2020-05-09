package com.ak.webApp.controller;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import com.ak.webApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class UserController {
    private UserRepository userRepository;
    private UserService userService;


    @Autowired
    public UserController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService=userService;
    }
    @PostMapping("/register")
    public String register(User user) {
        userService.createAndAddUser(user);
        return "redirect:/expenses";
    }


    @GetMapping(path = "/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }




}
