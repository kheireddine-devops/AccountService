package com.erp.ecommerce.common.services.specs;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountService {
    Customer addCustomer(Customer customer);
    List<Customer> getAllCustomers();

    List<Account> getAllAccounts();
    boolean existsAccountByUsername(String username);
    boolean existsAccountByEmail(String email);

    Optional<Account> getByUsernameOrEmail(String username, String email);
    boolean updateAccountStatus(UUID accountId, AccountStatusEnum status);

    Optional<Account> getAccountById(UUID accountId);

    boolean updateAccountTempCodeValidateEmail(UUID accountId, int verificationCode);

    String getEmailByAccountId(UUID accountId);
}
