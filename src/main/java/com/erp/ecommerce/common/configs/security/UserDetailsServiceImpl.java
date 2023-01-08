package com.erp.ecommerce.common.configs.security;

import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import com.erp.ecommerce.common.configs.exceptions.customs.NotFoundException;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account existingAccount = this.accountRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new NotFoundException(ExceptionsType.AUTH_ACTION_USERNAME_OR_EMAIL_NOT_FOUND));

        return existingAccount;
    }
}
