package com.erp.ecommerce.common.configs.security.authentication;

import com.erp.ecommerce.common.configs.security.authentication.CustomAuthenticationToken;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final AccountRepository accountRepository;
    @Value("${jwt.mapping.authority-prefix}") private String AUTHORITY_PREFIX;
    @Value("${jwt.mapping.authorities.claim-name}") private String AUTHORITIES_CLAIM_NAME;
    @Value("${jwt.mapping.principal.claim-name}") private String PRINCIPAL_CLAIM_NAME;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String subject = source.getClaimAsString(PRINCIPAL_CLAIM_NAME);
        Collection<GrantedAuthority> authorities = source
                .getClaimAsStringList(AUTHORITIES_CLAIM_NAME)
                .stream()
                .map(s -> new SimpleGrantedAuthority(AUTHORITY_PREFIX+s))
                .collect(Collectors.toList());

        Optional<Account> accountOptional = this.accountRepository.findByUsernameOrEmail(subject,subject);
        Account account = accountOptional.orElse(null);

        return new CustomAuthenticationToken(source,authorities,subject, Map.of("uid", account.getAccountId()));
    }
}