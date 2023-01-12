package com.erp.ecommerce.query.controllers.specs;

import com.erp.ecommerce.common.dtos.responses.AccountResponsesDTO;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface IAccountQueryController {
    @GetMapping({})
    ResponseEntity<List<AccountResponsesDTO.GetAccountDTO>> getAllAccounts();

    @GetMapping({"/customers"})
    ResponseEntity<List<Customer>> getAllCustomers();

    @GetMapping({"/{account-id}"})
    ResponseEntity<AccountResponsesDTO.GetAccountDTO> getAccountById(@PathVariable("account-id") UUID accountId);
}
