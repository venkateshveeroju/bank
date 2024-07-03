package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.mapper.UserMapper;
import com.bank.model.UserInfo;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private UserMapper UserMapper;
    @Autowired
    private AccountRepository accountRepository;
    public UserInfo getUserById(String accountNumber){
        Account accountObj = accountRepository.findByAccountNumber(accountNumber);
        Optional<User> User = UserRepository.findById(accountObj.getUser().getId());


        return UserMapper.convertToAccountM( User.get());
    }
}
