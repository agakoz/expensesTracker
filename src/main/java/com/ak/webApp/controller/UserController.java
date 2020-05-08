package com.ak.webApp.controller;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class UserController {
    private UserRepository userRepository;


    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
       // this.user= new User();
    }
    @GetMapping("/welcome-page")
    public String welcomePage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "welcome-page";
    }


    @GetMapping(path = "/users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }




}
