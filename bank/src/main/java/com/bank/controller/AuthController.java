package com.bank.controller;

import com.bank.model.LoginRequest;
import com.bank.model.LoginResponse;
import com.bank.security.JwtIssuer;
import com.bank.security.UserPriciple;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

        @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principle = (UserPriciple) authentication.getPrincipal();
            var roles = principle.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            var token = jwtIssuer.issue(principle.getUserId(), principle.getEmail(), roles);
            return LoginResponse.builder()
                    .accessToken(token)
                    .build();

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        return null;
    }}