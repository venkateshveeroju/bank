package com.bank.controller;

import com.bank.api.TransferApi;
import com.bank.entity.Transaction;
import com.bank.model.TransactionM;
import com.bank.model.TransactionRequest;
import com.bank.model.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.service.TransactionServiceImpl;
import com.bank.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Hidden
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController implements TransferApi {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<TransactionM> transferId(TransferRequest body) {
        TransactionM transactionM = transactionServiceImpl.transfer(body);
        return ResponseEntity.ok(transactionM);
    }


}

