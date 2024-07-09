package com.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String testUser() {
        return "Hello USER ";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin() {
        return "Hello ADMIN ";
    }
}
