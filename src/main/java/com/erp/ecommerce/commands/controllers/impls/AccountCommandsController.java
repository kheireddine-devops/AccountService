package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAccountCommandsController;
import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.requests.CustomerAccountRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping({"/accounts"})
@Log4j2
public class AccountCommandsController implements IAccountCommandsController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private AccountsMapper accountsMapper;
    @Override
    public CompletableFuture<String> addCustomer(CustomerAccountRequestDTO customerAccountRequestDTO) {
        AccountCommands.AddCustomerAccountCommand addCustomerAccountCommand = accountsMapper.fromCustomerAccountRequestDTOToAddCustomerAccountCommand(customerAccountRequestDTO);
        log.info(customerAccountRequestDTO);
        log.info(addCustomerAccountCommand);
        return this.commandGateway.send(addCustomerAccountCommand);
    }
}
