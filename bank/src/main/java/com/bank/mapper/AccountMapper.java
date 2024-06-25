package com.bank.mapper;

import com.bank.entity.Account;
import com.bank.model.AccountM;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public  AccountM convertToAccountM(Account account){
        AccountM accountM = new AccountM();
        accountM.setStatus(account.getStatus().toString());
        accountM.setAccountNumber(account.getAccountNumber());
        accountM.setBalance(account.getBalance());
        accountM.setCreatedTimeStamp(account.getCreatedTimeStamp());
        accountM.setUpdatedTimeStamp(account.getUpdatedTimeStamp());
        return accountM;
    }
}
