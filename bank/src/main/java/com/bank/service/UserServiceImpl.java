package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.mapper.UserMapper;
import com.bank.model.LoginResponse;
import com.bank.model.UserInfo;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.security.JwtIssuer;
import com.bank.security.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private final JwtIssuer jwtIssuer;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountRepository accountRepository;

    public UserInfo getUserById(String accountNumber) {
        Account accountObj = accountRepository.findByAccountNumber(accountNumber);
        Optional<User> user = userRepository.findById(accountObj.getUser().getId());
        return userMapper.convertToAccountM(user.get());
    }

    public LoginResponse loginUser(String email, String password) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principle = (UserPrinciple) authentication.getPrincipal();
            var roles = principle.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issue(principle.getUserId(), principle.getEmail(), roles );
            return LoginResponse.builder()
                    .accessToken(token)
                    .build();

        } catch (UsernameNotFoundException userEx){
            logger.error("Invalid credentials ");

    }catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        return null;
    }
}
