package com.bank.controller;



import com.bank.api.CustomerApi;
import com.bank.model.CustomerInfo;
import com.bank.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class CustomerController implements CustomerApi {
    @Autowired
    CustomerServiceImpl customerServiceImpl;


    @Override
    public ResponseEntity<CustomerInfo> customerId(String accountNumber) {
        return ResponseEntity.ok(customerServiceImpl.getCustomerById(accountNumber));
    }
}
