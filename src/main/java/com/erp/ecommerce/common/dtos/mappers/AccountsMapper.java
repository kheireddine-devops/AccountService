package com.erp.ecommerce.common.dtos.mappers;

import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.actions.events.AccountEvents;
import com.erp.ecommerce.common.dtos.requests.CustomerAccountRequestDTO;
import com.erp.ecommerce.common.dtos.responses.CustomerAccountResponseDTO;
import com.erp.ecommerce.common.models.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountsMapper {
    Customer fromCustomerAccountRequestDTOToCustomer(CustomerAccountRequestDTO requestDTO);
    CustomerAccountResponseDTO fromCustomerToCustomerAccountResponseDTO(Customer entity);
    AccountCommands.AddCustomerAccountCommand fromCustomerAccountRequestDTOToAddCustomerAccountCommand(CustomerAccountRequestDTO requestDTO);
    AccountEvents.AddCustomerAccountEvent fromAddCustomerAccountCommandToAddCustomerAccountEvent(AccountCommands.AddCustomerAccountCommand command);
}
