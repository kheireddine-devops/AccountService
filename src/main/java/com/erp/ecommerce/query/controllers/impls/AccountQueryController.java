package com.erp.ecommerce.query.controllers.impls;

import com.erp.ecommerce.common.actions.query.AccountQuery;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.responses.AccountResponsesDTO;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.query.controllers.specs.IAccountQueryController;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/accounts"})
public class AccountQueryController implements IAccountQueryController {
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private AccountsMapper accountsMapper;

    @Override
    public ResponseEntity<List<AccountResponsesDTO.GetAccountDTO>> getAllAccounts() {
        List<Account> accounts = queryGateway.query(new AccountQuery.GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        var r = accounts.stream().map(this.accountsMapper::fromAccountToGetAccountDTO).collect(Collectors.toList());
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = queryGateway.query(new AccountQuery.GetAllCustomerAccountsQuery(), ResponseTypes.multipleInstancesOf(Customer.class)).join();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountResponsesDTO.GetAccountDTO> getAccountById(UUID accountId) {
        Optional<Account> account = queryGateway.query(new AccountQuery.GetAccountByIdQuery(accountId), ResponseTypes.optionalInstanceOf(Account.class)).join();
        return new ResponseEntity<>(accountsMapper.fromAccountToGetAccountDTO(account.orElse(null)), HttpStatus.OK);
    }
}
