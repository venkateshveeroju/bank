package com.bank.security;

import com.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByEmail(username).orElseThrow();
        String [] roles = user.getRole().split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities=new ArrayList<>();
       for(String role: roles){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return UserPriciple.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(simpleGrantedAuthorities)
                .password(user.getPassword())
                .build();
    }
}
