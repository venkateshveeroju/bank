package com.bank.service;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final String Existing_Email = null;
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {

        Optional<User> user = null;

        if (email != null) {
            user = userRepository.findByEmail(email);
            user = Optional.of(user.get());
        }
        if (user == null) {
            return Optional.empty();
        }
        var userObj = new User();
        //check password encoder
        userObj.setPassword(user.get().getPassword());
        userObj.setEmail(user.get().getEmail());
        userObj.setRole(user.get().getRole());
        return user;
    }

}
