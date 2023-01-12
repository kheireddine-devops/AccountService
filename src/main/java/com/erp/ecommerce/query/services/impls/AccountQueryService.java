package com.erp.ecommerce.query.services.impls;

import com.erp.ecommerce.common.actions.query.AccountQuery;
import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import com.erp.ecommerce.common.configs.exceptions.customs.NotFoundException;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.services.specs.IAccountService;
import com.erp.ecommerce.query.services.specs.IAccountQueryService;
import lombok.extern.log4j.Log4j2;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AccountQueryService implements IAccountQueryService {
    @Autowired
    private IAccountService accountService;

    @Override
    @QueryHandler
    public List<Account> handle(AccountQuery.GetAllAccountsQuery query) {
        log.info(query);
        return this.accountService.getAllAccounts();
    }

    @Override
    @QueryHandler
    public List<Customer> handle(AccountQuery.GetAllCustomerAccountsQuery query) {
        log.info(query);
        return this.accountService.getAllCustomers();
    }

    @Override
    @QueryHandler
    public Optional<Account> handle(AccountQuery.GetAccountByIdQuery query) {
        log.info(query);
        return this.accountService.getAccountById(query.accountId());
    }
}
