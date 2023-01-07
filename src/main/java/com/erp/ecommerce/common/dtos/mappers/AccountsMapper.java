package com.erp.ecommerce.common.dtos.mappers;

import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import com.erp.ecommerce.common.dtos.responses.AccountResponsesDTO;
import com.erp.ecommerce.common.models.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountsMapper {
    AccountCommands.CreateCustomerAccountCommand fromAddCustomerAccountDTOToCreateCustomerAccountCommand(AccountRequestsDTO.CreateCustomerAccountDTO requestDTO);
    AccountResponsesDTO.GetAccountDTO fromAccountToGetAccountDTO(Account account);
}
