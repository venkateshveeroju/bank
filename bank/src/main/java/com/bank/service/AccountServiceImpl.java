package com.bank.service;


import com.bank.api.AccountsApi;
import com.bank.entity.Account;
import com.bank.entity.Address;
import com.bank.entity.User;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.*;
import com.bank.repository.AccountRepository;
import com.bank.repository.AddressRepository;
import com.bank.repository.RoleRepository;
import com.bank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;


@Service
public class AccountServiceImpl {

    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private  AccountRepository accountRepository;
    @Autowired
    private  UserRepository UserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;
    public ResponseEntity<UserCreated> createAccount(NewAccount body) {

            String str = UserRepository.findByEmail(body.getUser().getEmail());
            if ((body.getUser().getEmail() != null) && (str != null) && (!str.isEmpty())) {
                UserCreated UserCreated = new UserCreated();
                return ResponseEntity.ok(UserCreated);
            }
            UserCreated userCreated;
            try {
                Date dateOne = new Date();
                Instant inst = Instant.now();

                Address address = new Address();
                address.setStreet(body.getUser().getAddress().getStreet());
                address.setCity(body.getUser().getAddress().getCity());
                address.setState(body.getUser().getAddress().getState());
                address.setCountry(body.getUser().getAddress().getCountry());
                address.setPostalCode(body.getUser().getAddress().getPostalCode());

                Account acc = new Account();
                acc.setAccountNumber(body.getUser().getName() + "123");
                acc.setBalance(body.getUser().getAccount().getBalance());
                acc.setStatus(Status.ACTIVE);
                acc.setCreatedTimeStamp(dateOne.from(inst));
                acc.setUpdatedTimeStamp(dateOne.from(inst));


                User user = new User();
                user.setName(body.getUser().getName());
                user.setEmail(body.getUser().getEmail());
                user.setPassword(passwordEncoder.encode(body.getUser().getPassword()));
                user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
                user.setEnabled(true);
                user.setAccount(acc);
                user.setAddress(address);

                acc.setUser(user);
                address.setUser(user);
                acc.setTransactionList(null);
                addressRepository.save(address);
                Account ac = accountRepository.save(acc);
                UserRepository.save(user);
                userCreated = accountMapper.convertToUserCreated(ac);
                logger.info("Account creation successful " + userCreated.toString());
                return ResponseEntity.ok(userCreated);
            } catch (Exception ex) {
            logger.error(ex.getMessage());
                return null;
             }

    }


    public AccountM getAccount(String accountNumber) {
        AccountM accM = accountMapper.convertToAccountM(accountRepository.findByAccountNumber(accountNumber));
        return accM;
    }


}
