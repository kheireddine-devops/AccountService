package com.erp.ecommerce.common.configs.security;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account existingAccount = this.accountRepository.findByUsernameOrEmail(username,username)
                .orElseThrow(() -> new RuntimeException("ACCOUNT-NOT-FOUND"));

//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(existingAccount.getRole().toString()));
//        Account account = new Account();
//        account.setAccountId(existingAccount.getAccountId());
//        return account;

        return existingAccount;
    }
}
