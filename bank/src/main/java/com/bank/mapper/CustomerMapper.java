package com.bank.mapper;

import com.bank.entity.Account;
import com.bank.entity.Address;
import com.bank.entity.Customer;
import com.bank.model.AccountM;
import com.bank.model.AddressM;
import com.bank.model.CustomerM;
import org.hibernate.cache.spi.support.AbstractCachedDomainDataAccess;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerM convertToAccountM(Customer customer){
        CustomerM customerM = new CustomerM();
        AddressM address = new AddressM();
        address.setStreet(customer.getAddress().getStreet());
        address.setCity(customer.getAddress().getCity());
        address.setState(customer.getAddress().getState());
        address.setCountry(customer.getAddress().getCountry());
        address.setPostalCode(customer.getAddress().getPostalCode());
        customerM.setAddress(address);
        customerM.setEmail(customer.getEmail());
        customerM.setName(customer.getName());
        return customerM;
    }
}
