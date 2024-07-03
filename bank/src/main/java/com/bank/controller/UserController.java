package com.bank.controller;



import com.bank.api.UserApi;
import com.bank.model.UserInfo;
import com.bank.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class UserController implements UserApi {
    @Autowired
    UserServiceImpl UserServiceImpl;


    @Override
    public ResponseEntity<UserInfo> userId(String accountNumber) {
        return null;
    }
}
