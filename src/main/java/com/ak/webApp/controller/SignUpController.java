package com.ak.webApp.controller;

import com.ak.webApp.models.User;
import com.ak.webApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;

@Controller
public class SignUpController {

    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }



//    @GetMapping("/hello")
//    public String helloAdmin(Principal principal, Model model) {
//        model.addAttribute("name", principal.getName());
//        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        model.addAttribute("authorities", authorities);
//        model.addAttribute("details", details);
//        return "hello";
//    }

 

    @GetMapping("/sign-up")
    public String singUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }
    @PostMapping("/register")
    public String register(User user) {
        userService.createAndAddUser(user);
        return "redirect:/allExpenses";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/welcome-page"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

}