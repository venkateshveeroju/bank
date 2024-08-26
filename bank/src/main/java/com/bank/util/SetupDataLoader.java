package com.bank.util;

import com.bank.entity.Address;
import com.bank.entity.Privilege;
import com.bank.entity.Role;
import com.bank.entity.User;
import com.bank.mapper.UserMapper;
import com.bank.repository.PrivilegeRepository;
import com.bank.repository.RoleRepository;
import com.bank.repository.UserRepository;
import com.bank.service.AccountServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege
                = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        Set<Privilege> adminPrivileges = new HashSet<>();
        adminPrivileges.add(readPrivilege);
        adminPrivileges.add(writePrivilege);
        adminPrivileges.add(deletePrivilege);

        Set<Privilege> userPrivileges = new HashSet<>();
        userPrivileges.add(readPrivilege);
        userPrivileges.add(writePrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(adminRole);

        User user = new User();
        user.setName("Admin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("admin@gmail.com");
        user.setRoles(roleSet);

        Address address = new Address();
        address.setStreet("Admin street");
        address.setCity("Admin City");
        address.setState("Admin State");
        address.setCountry("Admin Country");
        address.setPostalCode("Admin Postal Code");

        address.setUser(user);
        user.setAddress(address);

        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);

            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}