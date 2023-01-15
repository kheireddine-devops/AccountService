package com.erp.ecommerce.common.configs.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;

@Getter @Setter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String principal;
    private Map<String,Object> details;

    public CustomAuthenticationToken(Jwt source, Collection<? extends GrantedAuthority> authorities, String principal, Map<String,Object> details) {
        super(authorities);
        this.setPrincipal(principal);
        this.setAuthenticated(true);
        this.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
