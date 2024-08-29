package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.mapper.AccountMapper;
import com.bank.model.TransactionM;
import com.bank.model.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserRepository;
import com.bank.security.UserPrinciple;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional(rollbackOn = RuntimeException.class)
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
        } else {
            logger.error("Account does not posses sufficient balance to make the transaction");
            throw new IllegalArgumentException("Account does not posses sufficient balance to make the transaction. Available balance is : ");//"The account balance is not sufficient to make transaction, available balance :  "+ accountRepository.findByAccountNumber(accountNumber).getBalance());
        }
    }

    public void saveBalanceByAcctID(String destAcctNumber, BigDecimal amount) {
        if (accountRepository.findByAccountNumber(destAcctNumber) == null) {
            logger.error("Receiver Account does not exists in our Bank");
            throw new IllegalArgumentException("Receiver Account does not exists in our Bank");
        }
        BigDecimal finalBalance = accountRepository.findBalanceByAcctID(destAcctNumber).add(amount);
        accountRepository.saveBalanceByAcctID(destAcctNumber, finalBalance);
    }

    public TransactionM transfer(TransferRequest body) {
        this.validateTransferRequest(body);
        Optional<Account> account = accountRepository.findByAccountNumber(body.getSenderAccount());
        if (account.get().getId() != UserPrinciple.builder().build().getUserId()) {
            logger.error("You are not authorized to make a transfer");
            throw new IllegalArgumentException("You are not authorized to make a transfer");
        }
        TransactionM transactionM = this.transferAmount(
                account.get().getId(), account.get().getUser().getName(), body.getSenderAccount(), body.getReceiverAccount(), body.getAmount());
        return transactionM;
    }

    public TransactionM transferAmount(@NonNull Long senderId, @NonNull String user, @NonNull String senderAccNumber, @NonNull String recvAccNumber, @NonNull BigDecimal amount) {
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

    public void validateTransferRequest(@NonNull TransferRequest body) {
        BigDecimal amount = body.getAmount();
        if (body.getSenderAccount() == null || body.getSenderAccount().isEmpty()) {
            logger.error("Sender Account Number cannot be empty ");
            throw new IllegalArgumentException("Sender Account Number cannot be empty ");
        } else if (body.getReceiverAccount() == null || body.getReceiverAccount().isEmpty()) {
            logger.error("Receiver Account Number cannot be empty");
            throw new IllegalArgumentException("Receiver Account Number cannot be empty ");
        } else if (body.getAmount() == null) {
            logger.error("Amount cannot be empty ");
            throw new IllegalArgumentException("Amount cannot be empty ");
        }
    }
}