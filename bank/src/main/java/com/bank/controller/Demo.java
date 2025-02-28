package com.bank.controller;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class Demo {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/thyme")
    public String getAllUsers(Model model, @ModelAttribute("user") User user){
        List<User> users= userRepository.findAll();
        model.addAttribute("users", users);
        return "thyme";
    }






}
