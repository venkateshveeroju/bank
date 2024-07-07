package com.bank.service;

import com.bank.entity.User;
import com.bank.model.UserCreated;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private static final String Existing_Email=null;
    public Optional<User> findByEmail(String email){

        Optional<User> result = null;
        User theUser = null;
        if(email != null) {
         result = Optional.ofNullable(userRepository.findFirstByEmailLike(email));

            if(result.isPresent()) {
                theUser = result.get();
            }
        }

            if (theUser != null) {
                return Optional.empty();
            }
            var user = new User();
            user.setPassword(passwordEncoder.encode(theUser.getPassword()));
            user.setEmail(theUser.getEmail());
            user.setRole(theUser.getRole());
            return Optional.of(user);
    }

}
