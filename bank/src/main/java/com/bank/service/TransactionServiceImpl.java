package com.bank.service;

import com.bank.entity.Transaction;
import com.bank.mapper.AccountMapper;
import com.bank.model.TransactionM;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.Date;

@Transactional
@Service
public class TransactionServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransactionRepository transactionRepository;

    public void withdrawAmountByAcctID(String accountNumber, BigDecimal amount) {
        // Set precision to 10
        MathContext mc = new MathContext(10);
        // Using subtract() method
        BigDecimal diff = (accountRepository.findBalanceByAcctID(accountNumber)).subtract(amount);
        if (diff.compareTo(BigDecimal.ZERO) >= 0) {
            accountRepository.withdrawAmountByAcctID(accountNumber, diff);
        }/*else{
            throw new InvalidTransactionException();//"The account balance is not sufficient to make transaction, available balance :  "+ accountRepository.findByAccountNumber(accountNumber).getBalance());
        }*/
    }

    public void saveBalanceByAcctID(String destAcctNumber, BigDecimal amount) {
        BigDecimal finalBalance = accountRepository.findBalanceByAcctID(destAcctNumber).add(amount);
        accountRepository.saveBalanceByAcctID(destAcctNumber, finalBalance);
    }


    public TransactionM transferAmount(Long senderId, String user, String senderAccNumber, String recvAccNumber, BigDecimal amount) {

        //validation
        //authentication
        //authorisation
        //transaction processing
        //logging and notification

        this.withdrawAmountByAcctID(senderAccNumber, amount);
        this.saveBalanceByAcctID(recvAccNumber, amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSenderAccount(senderAccNumber);
        transaction.setReceiverAccount(recvAccNumber);
        transaction.setUpdatedTimeStamp(Date.from(Instant.now()));
        transaction.setLastModifiedBy(user);
        transaction.setLastUpdatedBy(user);
        transaction.setUserId(senderId);
        transaction.setCreatedTimeStamp(Date.from(Instant.now()));

        return accountMapper.convertToTransactionM(transaction);

    }
}