package com.bank.controller;

import com.bank.api.TransferApi;
import com.bank.entity.Account;
import com.bank.model.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController implements TransferApi {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<String> transferId(TransferRequest body) {
        Account account = accountRepository.findByAccountNumber(body.getSenderAccount());
        transactionServiceImpl.transferAmount(account.getId(), account.getUser().getName(), body.getSenderAccount(), body.getReceiverAccount(), body.getAmount());
        return ResponseEntity.ok("Transaction Successfull");
    }
}

