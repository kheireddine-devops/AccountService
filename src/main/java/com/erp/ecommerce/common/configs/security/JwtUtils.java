package com.erp.ecommerce.common.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    @Value("${jwt.refresh-token.expiration}")
    private int JWT_REFRESH_TOKEN_EXPIRATION = 30;
    @Value("${jwt.access-token.expiration}")
    private int JWT_ACCESS_TOKEN_EXPIRATION = 5;
    @Value("${jwt.claims.issuer}")
    private String JWT_CLAIMS_ISSUER;

    public String extractSubject(String token) {
        return this.decodeJwt(token).getSubject();
    }

    public <R> R extractClaim(String token,String claim) {
        return this.decodeJwt(token).getClaim(claim);
    }

    public Jwt decodeJwt(String token) {
        return this.jwtDecoder.decode(token);
    }

    public Map<? extends String,Object> extractAllClaims(String token) {
        return this.decodeJwt(token).getClaims();
    }

    public String generateAccessToken(String subject, Map<String,Object> claims) {
        Instant instant = Instant.now();
        Consumer<Map<String,Object>> claimsConsumer = existingClaims -> existingClaims.putAll(claims);

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(JWT_ACCESS_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .issuer(JWT_CLAIMS_ISSUER)
                .claims(claimsConsumer)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    public String generateRefreshToken(String subject) {
        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(JWT_REFRESH_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .issuer(JWT_CLAIMS_ISSUER)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefreshToken)).getTokenValue();
    }

    public Instant extractExpiration(String token) {
        return this.decodeJwt(token).getExpiresAt();
    }

    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).isBefore(Instant.now());
    }
}
