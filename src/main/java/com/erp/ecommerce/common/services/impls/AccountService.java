package com.erp.ecommerce.common.services.impls;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.repositories.AccountRepository;
import com.erp.ecommerce.common.repositories.CustomerRepository;
import com.erp.ecommerce.common.services.specs.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
}
