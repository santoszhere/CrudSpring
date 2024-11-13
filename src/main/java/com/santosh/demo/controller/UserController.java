package com.santosh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.santosh.demo.model.User;
import com.santosh.demo.repository.UserRepository;
import org.springframework.ui.Model;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // List all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list"; // Thymeleaf template to show all users
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create"; // Thymeleaf template to create a new user
    }

    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userRepository.save(user); // Save the new user to the database
        return "redirect:/users"; // Redirect to the user list after creating the user
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/edit";
    }// Thymeleaf template to edit user details

    // Update an existing user
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id); // Ensure the user ID is set correctly
        userRepository.save(user); // Save the updated user to the database
        return "redirect:/users"; // Redirect to the user list after updating

    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user); // Delete the user from the database
        return "redirect:/users"; // Redirect to the user list after deleting
    }

}
