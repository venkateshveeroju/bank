package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.mapper.UserMapper;
import com.bank.model.UserInfo;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountRepository accountRepository;
    public UserInfo getUserById(String accountNumber){
        Account accountObj = accountRepository.findByAccountNumber(accountNumber);
        Optional<User> user = userRepository.findById(accountObj.getUser().getId());
        return userMapper.convertToAccountM( user.get());
    }
}
