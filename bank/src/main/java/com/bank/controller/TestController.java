package com.bank.controller;

import com.bank.entity.User;
import com.bank.security.JwtIssuer;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private JwtIssuer jwtIssuer;
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public String user() {
        return "Hello User ";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String admin() {

        return "Hello Admin ";
    }

    @GetMapping("/role/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String roleUserMethod(){
        return "Hello, USER role is working";
    }
    @GetMapping("/role/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String roleAdminMethod(){
        return "Hello, USER role is working";
    }
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}
