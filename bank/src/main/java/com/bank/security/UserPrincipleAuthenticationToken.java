package com.bank.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class UserPrincipleAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPriciple priciple;
    public UserPrincipleAuthenticationToken( UserPriciple userPriciple) {
        super(userPriciple.getAuthorities());
        this.priciple = userPriciple;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return priciple;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}
