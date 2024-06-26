package com.bank.service;


import com.bank.api.AccountsApi;
import com.bank.entity.Account;
import com.bank.entity.Address;
import com.bank.entity.Customer;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.DepositRequest;
import com.bank.model.NewAccount;
import com.bank.repository.AccountRepository;
import com.bank.repository.AddressRepository;
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
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountMapper accountMapper;
    public ResponseEntity<String> createAccount(NewAccount body) {

            String str = customerRepository.findByEmail(body.getCustomer().getEmail());
            if ((body.getCustomer().getEmail() != null) && (str != null) && (!str.isEmpty())) {
                return ResponseEntity.ok("Account already exists : " + body.getCustomer().getName() + "123");
            }
            Date dateOne = new Date();
            Instant inst = Instant.now();

            Address address = new Address();
            address.setStreet(body.getCustomer().getAddress().getStreet());
            address.setCity(body.getCustomer().getAddress().getCity());
            address.setState(body.getCustomer().getAddress().getState());
            address.setCountry(body.getCustomer().getAddress().getCountry());
            address.setPostalCode(body.getCustomer().getAddress().getPostalCode());

            Account acc = new Account();
            acc.setAccountNumber(body.getCustomer().getName() + "123");
            acc.setBalance(body.getCustomer().getAccount().getBalance());
            acc.setStatus(Status.ACTIVE);
            acc.setCreatedTimeStamp(dateOne.from(inst));
            acc.setUpdatedTimeStamp(dateOne.from(inst));

            Customer cust = new Customer();
            cust.setName(body.getCustomer().getName());
            cust.setEmail(body.getCustomer().getEmail());
            cust.setPassword(body.getCustomer().getPassword());
            cust.setAccount(acc);
            cust.setAddress(address);

            customerRepository.save(cust);
        addressRepository.save(address);

            return ResponseEntity.ok("Account created successfully " + accountRepository.save(acc).toString());

    }


    public AccountM getAccount(String accountNumber) {
        AccountM accM = accountMapper.convertToAccountM(accountRepository.findByAccountNumber(accountNumber));
        return accM;
    }


}
