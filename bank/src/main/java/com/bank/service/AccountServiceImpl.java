package com.bank.service;


import com.bank.entity.Account;
import com.bank.entity.Address;
import com.bank.entity.Customer;
import com.bank.enums.Status;
import com.bank.model.NewAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
public class AccountServiceImpl {
    @Autowired
    private  AccountRepository accountRepository;
    @Autowired
    private  CustomerRepository customerRepository;
    /*public Account getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountId(accountNumber);
        AccountM accountM = new AccountM();
        accountM.setAccountBalance(account.getAccountBalance());
        accountM.setAccountType(account.getAccountType());

        accountM.setStatus(account.getStatus());
        CustomerM  customerM = new CustomerM();
        accountM.setCustomer();
        accountM.setDateCreated(account.getDateCreated());
        accountM.setLastActivity(account.getLastActivity());
        accountM.setCustomerName(account.getCustomer().getFirstName() + "," + account.getCustomer().getLastName());


        return ResponseEntity.ok(accountM);
       return  accountRepository.findByAccountId(accountNumber);
    }
*/
    public ResponseEntity<String> createAccount(NewAccount body) {
       String str=  customerRepository.customerExistsByEmail( body.getCustomer().getEmail() );
        if( (body.getCustomer().getEmail()!= null) && (str!=null) && (!str.isEmpty()))
        {
            return ResponseEntity.ok("Account already exists : " +body.getCustomer().getFirstName() + "123");
        }
        Date dateOne = new Date();
        Instant inst = Instant.now();

        Customer cust = new Customer();
        cust.setFirstName(body.getCustomer().getFirstName());
        cust.setLastName(body.getCustomer().getLastName());
        cust.setAccountBalance(body.getCustomer().getBalance());
        cust.setEmail( body.getCustomer().getEmail());
        cust.setDob(body.getCustomer().getDob());
        cust.setPassword(body.getCustomer().getPassword());
        cust.setStatus(Status.ACTIVE);
        cust.setUpdationDate(dateOne.from(inst));
        cust.setCreationDate(dateOne.from(inst));

        Address address = new Address();
        address.setCity(body.getAddress().getCity());
        address.setState(body.getAddress().getState());
        address.setCountry(body.getAddress().getCountry());
       //address.getPostalCode(body.getAddress().getPostalCode());

        Account acc = new Account();
        acc.setAccountNumber(body.getCustomer().getFirstName() + "123");
        acc.setAccountBalance(body.getCustomer().getBalance());
        acc.setStatus(Status.ACTIVE);
        acc.setDateCreated(dateOne.from(inst));
        acc.setLastActivity(dateOne.from(inst));
        acc.setCustomer(cust);
        acc.setAddress(address);

        customerRepository.save(cust);
        return ResponseEntity.ok("Account created successfully " + accountRepository.save(acc).toString());
    }
}
