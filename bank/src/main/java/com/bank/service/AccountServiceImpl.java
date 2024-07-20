package com.bank.service;


import com.bank.entity.*;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.repository.*;
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
        String email = body.getUser().getEmail();
        try {
            String str = userRepository.findEmailByEmail(email);
            if (str != null) {
                throw new IllegalArgumentException("User already exists in system with Email : " + email);
            }
            UserCreated userCreated = null;
            if ((body.getUser().getEmail() != null) && (str != null) && (!str.isEmpty())) {
                return userCreated;
            }

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
            user.setEmail(body.getUser().getEmail());
            
            user.setPassword(passwordEncoder.encode(body.getUser().getPassword()));
            Set<String> strRoles = new HashSet<>();
            strRoles.add("ADMIN");

            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName("ROLE_USER");
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_ADMIN":
                            Role pmRole = roleRepository.findByName("ROLE_ADMIN");
                            roles.add(pmRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName("ROLE_USER");
                            roles.add(userRole);
                    }
                });
            }

            List<String> privileges = new ArrayList<>();
            List<Privilege> collection = new ArrayList<>();
            for (Role role : roles) {
                privileges.add(role.getName());
                collection.addAll(role.getPrivileges());
            }

            Role role = new Role();
            role.setPrivileges(collection);
            roleRepository.save(role);
            Privilege privilegeObj = new Privilege();
            privilegeObj.setRoles(roles);
            privilegeRepository.save(privilegeObj);

            user.setRoles(roles);
            user.setAccount(acc);
            user.setAddress(address);

            acc.setUser(user);
            acc.setUpdatedTimeStamp(Date.from(Instant.now()));
            address.setUser(user);

            addressRepository.save(address);
            Account ac = accountRepository.save(acc);
            userRepository.save(user);
            userCreated = accountMapper.convertToUserCreated(ac);
            logger.info("Account creation successful " + userCreated.toString());
            return userCreated;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new UsernameNotFoundException("not exist ");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
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
}
