package com.bank.mapper;

import com.bank.entity.Account;
import com.bank.model.AccountM;
import com.bank.model.UserCreated;
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
    public UserCreated convertToUserCreated (Account account){
        UserCreated UserCreated = new UserCreated();
        UserCreated.setAccountNumber(account.getAccountNumber());
        UserCreated.setBalance(account.getBalance());
        //UserCreated.setId(BigDecimal.valueOf(account.getId()));
        UserCreated.setCreatedTimeStamp(account.getCreatedTimeStamp());
        UserCreated.setUpdatedTimeStamp(account.getUpdatedTimeStamp());
        UserCreated.setStatus(account.getStatus().toString());
        return UserCreated;
    }
}
