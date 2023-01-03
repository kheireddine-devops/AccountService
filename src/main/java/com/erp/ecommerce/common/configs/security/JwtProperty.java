package com.erp.ecommerce.common.configs.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperty(AccessToken accessToken,RefreshToken refreshToken) {
    record AccessToken(Long expiration){}
    record RefreshToken(Long expiration){}
}
