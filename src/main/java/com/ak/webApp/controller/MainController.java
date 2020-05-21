package com.ak.webApp.controller;

import com.ak.webApp.models.User;
import com.ak.webApp.repository.UserRepository;
import com.ak.webApp.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private UserRepository userRepository;

    public MainController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/welcome-page")
    public String welcomePage(@RequestParam(value = "logout", required = false) String logout, Model model) {

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (null != logout) {
            model.addAttribute("logout", "You have been logged out");
        }
        return "welcome-page";
    }


    @GetMapping("/sign-up")
    public String singUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/welcome-page"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("/not-found")
    public String error(Model model) {

        return "not-found";
    }


}