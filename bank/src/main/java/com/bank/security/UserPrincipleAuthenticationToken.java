package com.bank.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;


public class UserPrincipleAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrinciple principle;

    public UserPrincipleAuthenticationToken(UserPrinciple userPrinciple) {
        super(userPrinciple.getAuthorities());
        this.principle = userPrinciple;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
