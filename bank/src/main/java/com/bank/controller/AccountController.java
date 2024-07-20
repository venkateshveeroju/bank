package com.bank.controller;


import com.bank.api.AccountsApi;
import com.bank.model.AccountM;
import com.bank.model.DepositRequest;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.service.AccountServiceImpl;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/v1")
public class AccountController implements AccountsApi {
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public ResponseEntity<UserCreated> accountId(@NonNull NewAccount newAccount) {
        if(newAccount==null || newAccount.getUser().getEmail()==null || newAccount.getUser().getEmail().isEmpty()) {
           throw new IllegalArgumentException("Email cannot be empty ");
        }
        return ResponseEntity.ok(accountService.createAccount(newAccount));
    }

    @Override
    public ResponseEntity<AccountM> accountsAccountNumberBalanceGet(@NonNull String accountNumber) {
        return new ResponseEntity<>(accountService.getAccount(accountNumber), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<AccountM> depositToAccount(@NonNull DepositRequest body) {
        return new ResponseEntity<>(accountService.depositToAccount( body.getAccountNumber(), body.getAmount()), HttpStatus.OK);
    }
}
