package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.mapper.UserMapper;
import com.bank.model.LoginResponse;
import com.bank.model.UserInfo;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.security.JwtIssuer;
import com.bank.security.UserPrinciple;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private final JwtIssuer jwtIssuer;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountRepository accountRepository;

    public UserInfo getUserById(@NotNull String accountNumber) {
        Account accountObj = accountRepository.findByAccountNumber(accountNumber);
        UserInfo userInfo = null;
        try {
            Optional<User> user = userRepository.findById(accountObj.getUser().getId());
            userInfo = userMapper.convertToAccountM(user.get());
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("Account Number does not exist : " + accountNumber);
        }catch (BadCredentialsException bcEx) {
            logger.error("Credentials you provided were wrong, please check");
        } catch (NullPointerException e) {
            logger.error("User does not exist ", e);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        return userInfo;
    }

    public LoginResponse loginUser(@NotNull String email, @NotNull String password) {

        try {
            if (userService.findByEmail(email).get().getEmail() == null) {
                return null;
            }
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principle = (UserPrinciple) authentication.getPrincipal();
            var roles = principle.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issue(principle.getUserId(), principle.getEmail(), roles);
            return LoginResponse.builder()
                    .accessToken(token)
                    .build();

        } catch (UsernameNotFoundException userEx) {
            logger.error("Invalid credentials ");

        } catch (BadCredentialsException bcEx) {
            logger.error("Credentials you provided were wrong, please check");
        } catch (NullPointerException e) {
            logger.error("User does not exist ", e);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        return null;
    }
}
