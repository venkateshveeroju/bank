package com.bank.service;


import com.bank.entity.Account;
import com.bank.entity.Address;
import com.bank.entity.Role;
import com.bank.entity.User;
import com.bank.enums.Status;
import com.bank.exception.AccountCreationException;
import com.bank.exception.EmailAlreadyExistsException;
import com.bank.exception.RoleNotFoundException;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.repository.*;
import jakarta.transaction.Transactional;
import java.util.Date;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

        // Check if the email already exists
        if (userRepository.findEmailByEmail(email) != null) {
            throw new EmailAlreadyExistsException("User already exists in system with Email: " + email);
        }

        try {
            UserCreated userCreated;

            // Build Address object
            Address address = new Address();
            address.setStreet(body.getUser().getAddress().getStreet());
            address.setCity(body.getUser().getAddress().getCity());
            address.setState(body.getUser().getAddress().getState());
            address.setCountry(body.getUser().getAddress().getCountry());
            address.setPostalCode(body.getUser().getAddress().getPostalCode());

            // Build Account object
            Account acc = new Account();
            acc.setAccountNumber(generateAccountNumber());
            acc.setBalance(Optional.ofNullable(body.getUser().getAccount().getBalance()).orElse(BigDecimal.ZERO));  // Default to 0 if null
            String name = body.getUser().getName();
            acc.setStatus(Status.ACTIVE);
            Date Date = java.util.Date.from(Instant.now());
            acc.setCreatedTimeStamp(Date);
            acc.setUpdatedTimeStamp(Date);
            acc.setLastUpdatedBy(name);
            acc.setLastModifiedBy(name);

            // Build User object
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(body.getUser().getPassword()));

            // Fetch the user role
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                throw new RoleNotFoundException("Role 'ROLE_USER' not found.");
            }

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(userRole);

            // Set relationships between user, account, and address
            user.setRoles(roleSet);
            acc.setUser(user);
            address.setUser(user);
            user.setAccount(acc);
            user.setAddress(address);

            // Save user object to the database
            userRepository.save(user);

            // Convert Account to UserCreated response
            userCreated = accountMapper.convertToUserCreated(acc);

            // Log successful account creation
            logger.info("Account creation successful: " + userCreated.toString());

            return userCreated;

        } catch (EmailAlreadyExistsException ex) {
            logger.error("Email already exists: " + ex.getMessage());
            throw ex;  // Re-throw the exception so it's handled properly (e.g., in a global exception handler).

        } catch (RoleNotFoundException ex) {
            logger.error("Role not found: " + ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            // Catch any unexpected exceptions and log them
            logger.error("Error occurred during account creation: " + ex.getMessage(), ex);
            throw new AccountCreationException("An unexpected error occurred during account creation.");
        }
    }

    private String generateAccountNumber() {
        return "A" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public AccountM getAccount(@NonNull String accountNumber) {
        AccountM accM;
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new IllegalArgumentException("Account does not exist : " + accountNumber));
        accM = accountMapper.convertToAccountM(Optional.ofNullable(account));
        return accM;
    }

    @PreAuthorize("hasAuthority('DELETE')")
    public String deleteUserAccount(@NonNull String accountNumber) {
        AccountM accM;
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist : " + accountNumber);
        }
        int deleteRecord = accountRepository.deleteByAccountNumber(accountNumber);
        if (deleteRecord == 1) {
            return accountNumber;
        } else {
            return "Unable to delete ";
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public AccountM depositToAccount(@NonNull String accountNumber, BigDecimal depositAmount) {
        // Fetch the account once

        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Receiver Account does not exists in our Bank")
                ));
        // Check if the account exists
        if (!optionalAccount.isPresent()) {
            throw new IllegalArgumentException("Account does not exist: " + accountNumber);
        }
        // Update the account balance
        BigDecimal finalBalance = accountRepository.findBalanceByAcctID(accountNumber).add(depositAmount);
        accountRepository.saveBalanceByAcctID(accountNumber, finalBalance);
        // Fetch the updated account
        optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        // Convert to AccountM and return
        return accountMapper.convertToAccountM(optionalAccount);
    }

    private void validateAccount(NewAccount newAccount) {
        if (newAccount == null || newAccount.getUser().getEmail() == null || newAccount.getUser().getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty ");
        } else if (newAccount.getUser().getName() == null || newAccount.getUser().getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty ");
        }
    }
}
