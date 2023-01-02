package com.erp.ecommerce.common.services.specs;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;

import java.util.List;

public interface IAccountService {
    List<Customer> getAllCustomers();

    List<Account> getAllAccounts();
}
