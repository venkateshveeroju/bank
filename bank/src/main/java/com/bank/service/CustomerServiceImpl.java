package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.mapper.CustomerMapper;
import com.bank.model.CustomerInfo;
import com.bank.model.CustomerM;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl  {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountRepository accountRepository;
    public CustomerInfo getCustomerById(String accountNumber){
        Account accountObj = accountRepository.findByAccountNumber(accountNumber);
        Optional<Customer> customer = customerRepository.findById(accountObj.getCustomer().getId());


        return customerMapper.convertToAccountM( customer.get());
    }
}
