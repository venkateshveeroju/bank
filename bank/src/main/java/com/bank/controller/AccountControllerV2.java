package com.bank.controller;

import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v2/")
@RestController
public class AccountControllerV2 {
    @Autowired
    AccountServiceImpl accountService;
    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserCreated> createAccountWithDOB(NewAccount body) {
        return ResponseEntity.ok(accountService.createAccount(body));
    }


}
