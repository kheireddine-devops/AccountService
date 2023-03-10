package com.erp.ecommerce.query.services.specs;

import com.erp.ecommerce.common.actions.query.AccountQuery;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface IAccountQueryService {
    List<Account> handle(AccountQuery.GetAllAccountsQuery query);
    List<Customer> handle(AccountQuery.GetAllCustomerAccountsQuery query);
    Optional<Account> handle(AccountQuery.GetAccountByIdQuery query);
}
