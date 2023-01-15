package com.erp.ecommerce.common.configs.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperty(AccessToken accessToken,RefreshToken refreshToken,Mapping mapping,Rsa rsa,Claims claims) {
    record AccessToken(Long expiration){}
    record RefreshToken(Long expiration){}
    record Claims(String issuer) {}
    record Mapping(String AuthorityPrefix, String AuthoritiesClaimName, String PrincipalClaimName, String UserClaimName){}
    record Rsa(RSAPublicKey publicKey, RSAPrivateKey privateKey){}
}
