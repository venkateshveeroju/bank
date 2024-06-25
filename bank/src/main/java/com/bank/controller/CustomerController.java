package com.bank.controller;


import com.bank.api.CustomerApi;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class CustomerController  implements CustomerApi {
    @Autowired
    CustomerRepository customerRepository;


    @Override
    public ResponseEntity<Customer> customerId(BigDecimal customerId) {

        return ResponseEntity.ok(customerRepository.findByCustomerId(customerId.longValue()));
    }
}
