package com.bank.controller;


import com.bank.api.CustomerApi;
import com.bank.entity.Customer;
import com.bank.model.CustomerM;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class CustomerController implements CustomerApi {
    @Autowired
    CustomerServiceImpl customerService;


    @Override
    public ResponseEntity<CustomerM> customerId(String accountNumber) {
        customerService.getCustomerById(accountNumber);


        return ResponseEntity.ok(customerService.getCustomerById(accountNumber));
    }
}
