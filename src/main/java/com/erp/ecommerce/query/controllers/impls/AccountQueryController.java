package com.erp.ecommerce.query.controllers.impls;

import com.erp.ecommerce.common.actions.query.AccountQuery;
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

@RestController
@RequestMapping({"/accounts"})
public class AccountQueryController implements IAccountQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @Override
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = queryGateway.query(new AccountQuery.GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = queryGateway.query(new AccountQuery.GetAllCustomerAccountsQuery(), ResponseTypes.multipleInstancesOf(Customer.class)).join();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
