package com.bank.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipleConvertor {
    public UserPrinciple convert(DecodedJWT jwt) {
        return UserPrinciple.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .email(jwt.getClaim("e").asString())
                .authorities(extractAuthorityFromClaim(jwt))
                //.authorities(extractPrivilegeAuthorityFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorityFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("auth");

        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
    private List<SimpleGrantedAuthority> extractPrivilegeAuthorityFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("p");

        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
