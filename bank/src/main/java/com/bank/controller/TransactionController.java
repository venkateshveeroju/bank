package com.bank.controller;

import com.bank.entity.Account;
import com.bank.model.TransactionRequest;
import com.bank.repository.AccountRepository;
import com.bank.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transfer")
    public String transfer(@RequestBody TransactionRequest transactionRequest) {
        Account account = accountRepository.findByAccountNumber(transactionRequest.getSenderAccNumber());

        transactionServiceImpl.transferAmount(account.getId(),account.getUser().getName(), transactionRequest.getSenderAccNumber(), transactionRequest.getDestAcctNumber(), transactionRequest.getAmount());
        return "Transaction Successful";
    }
}
