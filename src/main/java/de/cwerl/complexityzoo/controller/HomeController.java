package de.cwerl.complexityzoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.cwerl.complexityzoo.security.User;
import de.cwerl.complexityzoo.security.UserRepository;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("title", "Home");
        return "redirect:/classes";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Log in");
        return "users/login";
    }
}
