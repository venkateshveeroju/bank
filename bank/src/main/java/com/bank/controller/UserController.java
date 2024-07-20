package com.bank.controller;


import com.bank.api.UserApi;
import com.bank.model.UserInfo;
import com.bank.model.UserM;
import com.bank.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UserApi {
    @Autowired
    UserServiceImpl userService;


    @Override
    public ResponseEntity<UserInfo> userId(String accountNumber) {
        userService.getUserById(accountNumber);
        return ResponseEntity.ok(userService.getUserById(accountNumber));
    }
}
