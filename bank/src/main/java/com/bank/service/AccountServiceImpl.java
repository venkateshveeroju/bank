package com.bank.service;


import com.bank.entity.*;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.repository.*;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;


@Service
@Transactional(rollbackOn = RuntimeException.class)
public class AccountServiceImpl {
    private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserCreated createAccount(NewAccount body) {
        validateAccount(body);
        String email = body.getUser().getEmail();

            String str = userRepository.findEmailByEmail(email);
            if (str != null && !str.isEmpty()) {
                throw new IllegalArgumentException("User already exists in system with Email : " + email);
            }
        try {
            UserCreated userCreated;


            Address address = new Address();
            address.setStreet(body.getUser().getAddress().getStreet());
            address.setCity(body.getUser().getAddress().getCity());
            address.setState(body.getUser().getAddress().getState());
            address.setCountry(body.getUser().getAddress().getCountry());
            address.setPostalCode(body.getUser().getAddress().getPostalCode());

            Account acc = new Account();
            acc.setAccountNumber(body.getUser().getName() + "001001");
            if (body.getUser().getAccount().getBalance() != null) {
                acc.setBalance(body.getUser().getAccount().getBalance());
            } else {
                acc.setBalance(new BigDecimal(0));
            }
            String name = body.getUser().getName();
            acc.setStatus(Status.ACTIVE);
            acc.setUpdatedTimeStamp(Date.from(Instant.now()));
            acc.setLastUpdatedBy(name);
            acc.setLastModifiedBy(name);

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(body.getUser().getPassword()));


            Role userRole = roleRepository.findByName("ROLE_USER");

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(userRole);


            user.setRoles(roleSet);
            user.setAccount(acc);
            user.setAddress(address);

            /*acc.setUser(user);
            acc.setUpdatedTimeStamp(Date.from(Instant.now()));
            address.setUser(user);

            addressRepository.save(address);
            Account ac = accountRepository.save(acc);*/
            userRepository.save(user);
            userCreated = accountMapper.convertToUserCreated(acc);
            logger.info("Account creation successful " + userCreated.toString());
            return userCreated;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new IllegalArgumentException("Unsuccessfull");
        }
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    public AccountM getAccount(@NonNull String accountNumber) {
        AccountM accM;
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist : " + accountNumber);
        }
        accM = accountMapper.convertToAccountM(account);
        return accM;
    }

    @PreAuthorize("hasAuthority('DELETE')")
    public String deleteUserAccount(@NonNull String accountNumber) {
        AccountM accM;
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist : " + accountNumber);
        }
        accountRepository.deleteByAccountNumber(accountNumber);
        return accountNumber;
    }

    @PreAuthorize("hasAuthority('READ')")
    public AccountM depositToAccount(@NonNull String accountNumber, BigDecimal depositAmount) {
        AccountM accM;
        if (accountRepository.findByAccountNumber(accountNumber) == null) {
            throw new IllegalArgumentException("Account does not exist : " + accountNumber);
        }
        accountRepository.saveBalanceByAcctID(accountNumber, depositAmount);
        Account account = accountRepository.findByAccountNumber(accountNumber);
        accM = accountMapper.convertToAccountM(account);
        return accM;
    }

    private void validateAccount(NewAccount newAccount) {
        if (newAccount == null || newAccount.getUser().getEmail() == null || newAccount.getUser().getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty ");
        } else if (newAccount.getUser().getName() == null || newAccount.getUser().getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty ");
        }
    }
}
