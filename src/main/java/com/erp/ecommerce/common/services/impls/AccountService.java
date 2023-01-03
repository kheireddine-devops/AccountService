package com.erp.ecommerce.common.services.impls;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.repositories.AccountRepository;
import com.erp.ecommerce.common.repositories.CustomerRepository;
import com.erp.ecommerce.common.services.specs.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder encoder;

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
}
