package com.bank.controller;




import com.bank.model.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.service.TransactionServiceImpl;
@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;

    @GetMapping( "/transfer")
    public String transfer(@RequestBody TransactionRequest transactionRequest){
        //transactionServiceImpl.transferAmount( transactionRequest.getSenderAccNumber(),  transactionRequest.getDestAcctNumber(),transactionRequest.getAmount());
        transactionServiceImpl.withdrawAmountByAcctID( transactionRequest.getSenderAccNumber(),  transactionRequest.getAmount());
        transactionServiceImpl.saveBalanceByAcctID( transactionRequest.getDestAcctNumber(),  transactionRequest.getAmount());
        return "Transaction Successful";
    }
}

