package com.bank.controller;





import com.bank.api.AccountsApi;
import com.bank.entity.Account;
import com.bank.model.*;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.service.AccountServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AccountController implements AccountsApi {
    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository UserRepository;


    @Override
    public ResponseEntity<UserCreated> accountId(NewAccount body) {
        return ResponseEntity.ok(accountService.createAccount(body).getBody());
    }

    @Override
    public ResponseEntity<AccountM> accountsAccountNumberBalanceGet(String accountNumber) {
        return  ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @Override
    public ResponseEntity<Void> depositToAccount(DepositRequest body) {
        return null;
    }
}
