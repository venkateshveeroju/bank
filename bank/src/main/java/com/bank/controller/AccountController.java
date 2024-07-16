package com.bank.controller;


import com.bank.api.AccountsApi;
import com.bank.model.AccountM;
import com.bank.model.DepositRequest;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class AccountController implements AccountsApi {
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public ResponseEntity<UserCreated> accountId(NewAccount body) {

        return ResponseEntity.ok(accountService.createAccount(body));
    }

    @Override
    public ResponseEntity<AccountM> accountsAccountNumberBalanceGet(String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @Override
    public ResponseEntity<Void> depositToAccount(DepositRequest body) {
        return null;
    }

}
