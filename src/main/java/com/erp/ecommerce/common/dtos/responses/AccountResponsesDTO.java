package com.erp.ecommerce.common.dtos.responses;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;

import java.util.UUID;

public interface AccountResponsesDTO {
    record CreateCustomerAccountDTO(UUID accountId){}
    record GetAccountDTO(UUID accountId, String username, String email, AccountStatusEnum status, AccountRoleEnum role){}
}