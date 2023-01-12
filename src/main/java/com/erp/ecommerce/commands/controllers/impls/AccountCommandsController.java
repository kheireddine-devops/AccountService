package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAccountCommandsController;
import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping({"/accounts"})
@Log4j2
@RequiredArgsConstructor
public class AccountCommandsController implements IAccountCommandsController {
    private final CommandGateway commandGateway;
    private final AccountsMapper accountsMapper;

    @Override
    public CompletableFuture<String> addCustomer(AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO) {
        AccountCommands.CreateCustomerAccountCommand createCustomerAccountCommand = accountsMapper.fromAddCustomerAccountDTOToCreateCustomerAccountCommand(createCustomerAccountDTO);
        return this.commandGateway.send(createCustomerAccountCommand);
    }
}
