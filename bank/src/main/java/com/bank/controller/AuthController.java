package com.bank.controller;

import com.bank.model.LoginRequest;
import com.bank.model.LoginResponse;
import com.bank.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return userServiceImpl.loginUser(request.getEmail(), request.getPassword());
    }
}
