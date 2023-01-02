package com.erp.ecommerce.common.actions.events;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.models.enums.GenderEnum;

import java.util.Date;
import java.util.UUID;

public interface AccountEvents {
    record AddCustomerAccountEvent(UUID customerId, String firstname, String lastname, GenderEnum gender, Date dateOfBirth, String email, String password, AccountRoleEnum role, AccountStatusEnum status) {};
    record ActivateCustomerAccountEvent(UUID customerId) {};
    record SendVerificationEmailEvent(UUID accountId, short verificationCode) {};
}
