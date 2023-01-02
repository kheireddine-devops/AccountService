package com.erp.ecommerce.query.controllers.specs;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface IAccountQueryController {
    @GetMapping({})
    ResponseEntity<List<Account>> getAllAccounts();

    @GetMapping({"/customers"})
    ResponseEntity<List<Customer>> getAllCustomers();
}
