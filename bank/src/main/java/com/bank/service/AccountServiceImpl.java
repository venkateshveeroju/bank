package com.bank.service;


import com.bank.entity.*;
import com.bank.enums.Status;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountM;
import com.bank.model.NewAccount;
import com.bank.model.UserCreated;
import com.bank.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public UserCreated createAccount(NewAccount body) {
        try {
            String str = userRepository.findEmailByEmail(body.getUser().getEmail());
            UserCreated userCreated = null;
            if ((body.getUser().getEmail() != null) && (str != null) && (!str.isEmpty())) {
                return userCreated;
            }
            Date dateOne = new Date();
            Instant inst = Instant.now();

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
            acc.setStatus(Status.ACTIVE);
            acc.setCreatedTimeStamp(dateOne.from(inst));
            acc.setUpdatedTimeStamp(dateOne.from(inst));


            User user = new User();
            user.setName(body.getUser().getName());
            user.setEmail(body.getUser().getEmail());
            //check password encoder bean
            user.setPassword(new BCryptPasswordEncoder().encode(body.getUser().getPassword()));
            Set<String> strRoles = new HashSet<>();
            strRoles.add("ADMIN");


            //user.setRole(body.getUser().getRoles());


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
            address.setUser(user);

            addressRepository.save(address);
            Account ac = accountRepository.save(acc);
            userRepository.save(user);
            userCreated = accountMapper.convertToUserCreated(ac);
            logger.info("Account creation successful " + userCreated.toString());
            return userCreated;
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
