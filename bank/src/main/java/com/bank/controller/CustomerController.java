package com.bank.controller;

import com.bank.api.CustomerApi;
import com.bank.model.CustomerM;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CustomerController implements CustomerApi {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerM customerId(BigDecimal customerId) {
        customerRepository.findByCustomerId(customerId);
        CustomerM customerM = new CustomerM();
        CustomerM ResponseEntity;
        return customerM;
    }
}
