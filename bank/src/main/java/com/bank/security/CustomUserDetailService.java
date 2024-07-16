package com.bank.security;

import com.bank.entity.Privilege;
import com.bank.entity.User;
import com.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bank.entity.Role;
import java.util.*;



@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userService.findByEmail(username);
        if (user == null) {
            logger.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return UserPrinciple.builder()
                .userId(user.get().getId())
                .email(user.get().getEmail())
                .authorities(getAuthorities(user.get().getRoles()))
                .password(user.get().getPassword())
                .build();
    }
    /*private Collection<? extends GrantedAuthority> getAuthorities(
            User user) {
        List<String> roleList = List.of(user.getRole().split(","));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roleList){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        }

       for (String role: roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
            grantedAuthorities.addAll(
                    .stream()
                    .map(p -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toList(getAuthorities()));
        }
        return grantedAuthorities;
    }*/
    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
