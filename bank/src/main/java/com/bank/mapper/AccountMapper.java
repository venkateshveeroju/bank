package com.bank.mapper;

import com.bank.entity.Account;
import com.bank.model.AccountM;
import com.bank.model.CustomerCreated;
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

    public CustomerCreated convertToCustomerCreated (Account account){
        CustomerCreated customerCreated = new CustomerCreated();
        customerCreated.setAccountNumber(account.getAccountNumber());
        customerCreated.setBalance(account.getBalance());

        customerCreated.setCreatedTimeStamp(account.getCreatedTimeStamp());
        customerCreated.setUpdatedTimeStamp(account.getUpdatedTimeStamp());
        customerCreated.setBalance(account.getBalance());
        return customerCreated;
    }
}
