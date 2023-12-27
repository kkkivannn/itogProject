package com.example.individualproject.controller;

import com.example.individualproject.models.RoleEnum;
import com.example.individualproject.models.UserModel;
import com.example.individualproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class registerController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/registration")
    private String showRegistrationForm() {
        return "regis";
    }
    @PostMapping("/registration")
    private String registerUser(UserModel user, Model model) {
        UserModel userFromDb = userRepository.findByUsernameContaining(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "regis";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }
}
