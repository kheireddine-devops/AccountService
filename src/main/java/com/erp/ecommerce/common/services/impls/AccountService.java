package com.erp.ecommerce.common.services.impls;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.repositories.AccountRepository;
import com.erp.ecommerce.common.repositories.CustomerRepository;
import com.erp.ecommerce.common.services.specs.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Customer addCustomer(Customer customer) {
        String hashedPassword = this.encoder.encode(customer.getAccount().getPassword());
        customer.getAccount().setPassword(hashedPassword);
        Customer savedCustomer = this.customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return customers;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = this.accountRepository.findAll();
        return accounts;
    }

    @Override
    public boolean existsAccountByUsername(String username) {
        return this.accountRepository.existsAccountByUsername(username);
    }

    @Override
    public boolean existsAccountByEmail(String email) {
        return this.accountRepository.existsAccountByEmail(email);
    }

    @Override
    public Optional<Account> getByUsernameOrEmail(String username, String email) {
        return this.accountRepository.findByUsernameOrEmail(username,email);
    }

    @Override
    public boolean updateAccountStatus(UUID accountId, AccountStatusEnum status) {
        return this.accountRepository.updateAccountStatus(accountId,status) == 1;
    }

    @Override
    public Optional<Account> getAccountById(UUID accountId) {
        return this.accountRepository.findById(accountId);
    }

    @Override
    public boolean updateAccountTempCodeValidateEmail(UUID accountId, int verificationCode) {
        return this.accountRepository.updateAccountTempCodeValidateEmail(accountId, verificationCode) == 1;
    }

    @Override
    public String getEmailByAccountId(UUID accountId) {
        return this.accountRepository.findEmailByAccountId(accountId);
    }
}
