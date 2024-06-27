package com.bank.mapper;

import com.bank.entity.Customer;
import com.bank.model.AddressM;
import com.bank.model.CustomerInfo;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerInfo convertToAccountM(Customer customer){
        AddressM address = new AddressM();
        address.setStreet(customer.getAddress().getStreet());
        address.setCity(customer.getAddress().getCity());
        address.setState(customer.getAddress().getState());
        address.setCountry(customer.getAddress().getCountry());
        address.setPostalCode(customer.getAddress().getPostalCode());



        CustomerInfo customerInfo = new CustomerInfo();

        customerInfo.setEmail(customer.getEmail());
        customerInfo.setName(customer.getName());

       customerInfo.setAddress(address);
        return customerInfo;
    }
}
