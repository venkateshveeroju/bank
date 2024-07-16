package com.bank.service;

import com.bank.entity.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


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
        userObj.setPassword(new BCryptPasswordEncoder().encode(user.get().getPassword()));
        userObj.setEmail(user.get().getEmail());
        userObj.setRoles(user.get().getRoles());
        return user;
    }

}
