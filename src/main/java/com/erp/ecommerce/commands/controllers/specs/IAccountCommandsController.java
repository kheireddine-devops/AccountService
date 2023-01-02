package com.erp.ecommerce.commands.controllers.specs;

import com.erp.ecommerce.common.dtos.requests.CustomerAccountRequestDTO;
import com.erp.ecommerce.common.models.entities.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IAccountCommandsController {
    @PostMapping({"/customers"})
    CompletableFuture<String> addCustomer(@RequestBody CustomerAccountRequestDTO customerAccountRequestDTO);
}
