package com.bank.controller;


import com.bank.api.AccountsApi;
import com.bank.entity.Transaction;
import com.bank.mapper.AccountMapper;
import com.bank.mapper.TransactionMapper;
import com.bank.model.*;
import com.bank.service.AccountServiceImpl;
import com.bank.service.TransactionServiceImpl;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@Valid
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController implements AccountsApi {
    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    TransactionServiceImpl transactionServiceImpl;

    @Autowired
    TransactionMapper transactionMapper;

    @Override
    public ResponseEntity<UserCreated> createAccount(NewAccount body) {
        return ResponseEntity.ok(accountService.createAccount(body));
    }

    @Override
    public ResponseEntity<BigDecimal> deleteUserByAccountNumber(String accountNumber) {
        int n = accountService.deleteAccount(accountNumber);
        String response = n == 1 ? "Account deleted successfully" : "Account is not deleted, please try again";
        return ResponseEntity.ok(BigDecimal.valueOf(n));
    }


    @Override
    public ResponseEntity<AccountM> depositToAccount(@NonNull DepositRequest body) {
        return new ResponseEntity<>(accountService.depositToAccount(body.getAccountNumber(), body.getAmount()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AccountM>> getAllAccounts() {
        return  accountService.getAllAccounts();
    }


    @Override
    public ResponseEntity<AccountM> getBalance(String accountNumber) {
        return new ResponseEntity<>(accountService.getAccount(accountNumber), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<TransactionM>> getFilteredTransactions(TransactionsFilteredBody body) {
        List<Transaction> transactionRequests = transactionServiceImpl.getFilteredTransactions(body.getAccountNumber(), body.getStartDate(), body.getEndDate(), body.getMinAmount(), body.getMaxAmount());
        return new ResponseEntity<>(transactionRequests.parallelStream().map(accountMapper::convertToTransactionM).collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<TransactionM>> getTransactionsOfAnAccount(String accountNumber) {
        List<Transaction> transactionRequests = transactionServiceImpl.getTransactions(accountNumber);
        return new ResponseEntity<>(transactionRequests.parallelStream().map(accountMapper::convertToTransactionM).collect(Collectors.toList()), HttpStatus.ACCEPTED);
    }
}
