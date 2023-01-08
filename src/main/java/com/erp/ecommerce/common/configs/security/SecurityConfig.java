package com.erp.ecommerce.common.configs.security;

import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import com.erp.ecommerce.common.configs.exceptions.customs.NotFoundException;
import com.erp.ecommerce.common.repositories.AccountRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    @Value("${jwt.rsa.public-key}") private RSAPublicKey JWT_RSA_PUBLIC_KEY;
    @Value("${jwt.rsa.private-key}") private RSAPrivateKey JWT_RSA_PRIVATE_KEY;
    @Value("${jwt.mapping.authority-prefix}") private String AUTHORITY_PREFIX;
    @Value("${jwt.mapping.authorities.claim-name}") private String AUTHORITIES_CLAIM_NAME;
    @Value("${jwt.mapping.principal.claim-name}") private String PRINCIPAL_CLAIM_NAME;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder().encode("admin")).authorities("ADMIN","STAFF").build(),
                User.withUsername("customer").password(passwordEncoder().encode("customer")).authorities("CUSTOMER").build(),
                User.withUsername("storekeeper").password(passwordEncoder().encode("storekeeper")).authorities("STAFF","STOREKEEPER").build(),
                User.withUsername("customer_service").password(passwordEncoder().encode("customer_service")).authorities("STAFF","CUSTOMER_SERVICE").build()
        );
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return username -> accountRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new NotFoundException(ExceptionsType.AUTH_ACTION_USERNAME_OR_EMAIL_NOT_FOUND));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        authenticationProvider.setUserDetailsService(this.userDetailsService());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.antMatchers(EndPointsConfigs.AUTH_WHITELIST).permitAll())
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_CLAIM_NAME);
        grantedAuthoritiesConverter.setAuthorityPrefix(AUTHORITY_PREFIX);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        jwtAuthenticationConverter.setPrincipalClaimName(PRINCIPAL_CLAIM_NAME);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(JWT_RSA_PUBLIC_KEY).privateKey(JWT_RSA_PRIVATE_KEY).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(JWT_RSA_PUBLIC_KEY).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
