package com.bank.controller;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import com.bank.security.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private JwtIssuer jwtIssuer;
    private JwtDecoder jwtDecoder;
    @Autowired
    private UserRepository userRepository;

    private UserPrinciple userPrinciple;

    @GetMapping("/user1")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE') && hasRole('ROLE_USER')")
    public String user(@AuthenticationPrincipal CustomUserDetailService customUserDetailService) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof Claims)
            return authentication.getDetails().toString();
        return "Hello User " + UserPrinciple.builder().build().getUserId();
    }

    @GetMapping("/admin1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {

        return "Hello Admin ";
    }

    @GetMapping("/role/user1")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String roleUserMethod() {
        return "Hello, USER role is working";
    }

    @GetMapping("/role/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String roleAdminMethod() {
        return "Hello, USER role is working";
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasRole('USER')")
    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
    @GetMapping("/users")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/user-id")
    @PreAuthorize("hasRole('USER')")
    public Long getUserId(Principal principal) {
        return ((UserPrinciple) ((UserPrincipleAuthenticationToken) principal).getPrincipal()).getUserId();
    }


}
