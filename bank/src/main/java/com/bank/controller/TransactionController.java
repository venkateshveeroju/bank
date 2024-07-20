package com.bank.controller;

import com.bank.api.TransferApi;
import com.bank.entity.Account;
import com.bank.model.TransactionM;
import com.bank.model.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.security.UserPrinciple;
import com.bank.service.TransactionServiceImpl;
import com.bank.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Hidden
public class TransactionController implements TransferApi {
    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<TransactionM> transferId(TransferRequest body) {
        Account account = accountRepository.findByAccountNumber(body.getSenderAccount());
        if (account.getId() != UserPrinciple.builder().build().getUserId()) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "You are not authorized to make a transfer");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionM transactionM = transactionServiceImpl.transferAmount(
                account.getId(), account.getUser().getName(), body.getSenderAccount(), body.getReceiverAccount(), body.getAmount());
        return ResponseEntity.ok(transactionM);
    }
}

