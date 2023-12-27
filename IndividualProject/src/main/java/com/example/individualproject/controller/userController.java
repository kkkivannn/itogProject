package com.example.individualproject.controller;

import com.example.individualproject.models.RoleEnum;
import com.example.individualproject.models.UserModel;
import com.example.individualproject.models.UserWithRolesDTO;
import com.example.individualproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class userController {
    private final UserRepository userRepository;

    @Autowired
    public userController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        List<UserWithRolesDTO> usersWithRoles = new ArrayList<>();
        Iterable<UserModel> users = userRepository.findAll();

        for (UserModel user : users) {
            UserWithRolesDTO userWithRoles = new UserWithRolesDTO();
            userWithRoles.setUser(user);
            userWithRoles.setRole(user.getRoles().iterator().next());  // Assuming each user has only one role
            usersWithRoles.add(userWithRoles);
        }

        model.addAttribute("users", usersWithRoles);
        return "admin/users/allUsers";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/users/updateUser";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, @RequestParam("role") RoleEnum role, Model model) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        System.out.println("Updating user with ID: " + id);
        System.out.println("New role: " + role);

        Set<RoleEnum> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        List<UserWithRolesDTO> usersWithRoles = new ArrayList<>();
        Iterable<UserModel> users = userRepository.findAll();

        for (UserModel userModel : users) {
            UserWithRolesDTO userWithRoles = new UserWithRolesDTO();
            userWithRoles.setUser(userModel);
            userWithRoles.setRole(userModel.getRoles().iterator().next());  // Assuming each user has only one role
            usersWithRoles.add(userWithRoles);
        }

        model.addAttribute("users", usersWithRoles);

        return "redirect:/allUsers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/allUsers";
    }
}
