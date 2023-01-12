package com.erp.ecommerce.commands.controllers.specs;

import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Account", description = "EndPoints for managing Accounts")
public interface IAccountCommandsController {

    @PostMapping({"/register/customer"})
    CompletableFuture<String> addCustomer(@RequestBody @Valid AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO);

}
