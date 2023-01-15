package com.erp.ecommerce.common.configs.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;

@Getter @Setter
public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {

    private String principal;
    private Map<String,Object> details;

    public CustomJwtAuthenticationToken(Jwt source, Collection<? extends GrantedAuthority> authorities, String principal, Map<String,Object> details) {
        super(source);
        this.setPrincipal(principal);
        this.setAuthenticated(true);
        this.setDetails(details);
    }
}
