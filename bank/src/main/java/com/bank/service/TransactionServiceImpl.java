package com.bank.service;

import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.apache.el.lang.ELArithmetic.subtract;

@Transactional
@Service
public class TransactionServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository UserRepository;

    public void withdrawAmountByAcctID(String accountNumber, BigDecimal amount){
        // Set precision to 10
        MathContext mc
                = new MathContext(10);
        // Using subtract() method
        BigDecimal diff = (accountRepository.findBalanceByAcctID(accountNumber)).subtract(amount);
        if(diff.compareTo(BigDecimal.ZERO)>=0)
            accountRepository.withdrawAmountByAcctID( accountNumber, diff);{
        }
    }
    public void saveBalanceByAcctID(String destAcctNumber, BigDecimal amount){
       BigDecimal finalBalance = accountRepository.findBalanceByAcctID(destAcctNumber).add(amount);
        accountRepository.saveBalanceByAcctID( destAcctNumber, finalBalance);{

        }
    }

    public void transferAmount(String accountNumber,  String destAcctNumber, BigDecimal amount) {
        //validation
        //authentication
        //authorisation
        //transaction processing
        //logging and notification

        accountRepository.withdrawAmountByAcctID(accountNumber, amount);
        accountRepository.saveBalanceByAcctID(destAcctNumber, amount);


    }
}
