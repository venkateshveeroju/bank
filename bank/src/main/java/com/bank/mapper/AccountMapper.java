package com.bank.mapper;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.model.AccountM;
import com.bank.model.TransactionM;
import com.bank.model.UserCreated;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountMapper {
    public AccountM convertToAccountM(Optional<Account> account) {
        if (account.isEmpty() || !account.isPresent()) {
            throw new IllegalArgumentException("Invalid Account");
        }
        AccountM accountM = new AccountM();
        accountM.setStatus(account.get().getStatus().toString());
        accountM.setAccountNumber(account.get().getAccountNumber());
        accountM.setBalance(account.get().getBalance());
        accountM.setUpdatedTimeStamp(account.get().getUpdatedTimeStamp());
        return accountM;
    }

    public UserCreated convertToUserCreated(Account account) {
        UserCreated UserCreated = new UserCreated();
        UserCreated.setAccountNumber(account.getAccountNumber());
        UserCreated.setBalance(account.getBalance());
        UserCreated.setCreatedTimeStamp(account.getCreatedTimeStamp());
        UserCreated.setUpdatedTimeStamp(account.getUpdatedTimeStamp());
        UserCreated.setStatus(account.getStatus().toString());
        return UserCreated;
    }

    public TransactionM convertToTransactionM(Transaction transaction) {
        TransactionM transactionM = new TransactionM();
       // transactionM.setSenderId(BigDecimal.valueOf(transaction.getUserId()));
        transactionM.setAmount(transaction.getAmount());
        transactionM.setReceiverAccount(transaction.getReceiverAccount());
        transactionM.setSenderAccount(transaction.getSenderAccount());
        transactionM.setCreatedTimeStamp(transaction.getCreatedTimeStamp());
        transactionM.setTransactionId(transaction.getRandomUUId().toString());
        transactionM.setTransactionBy(transaction.getLastModifiedBy());
        return transactionM;
    }
}
