package com.erp.ecommerce.common.actions.commands;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.models.enums.GenderEnum;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
import java.util.UUID;

public interface AccountCommands {
    record AddCustomerAccountCommand(@TargetAggregateIdentifier UUID customerId, String firstname, String lastname, GenderEnum gender, Date dateOfBirth, String email, String password, AccountRoleEnum role, AccountStatusEnum status) {};
    record ActivateCustomerAccountCommand(@TargetAggregateIdentifier UUID customerId) {};
    record SendVerificationEmailCommand(@TargetAggregateIdentifier UUID accountId, short verificationCode) {};
}
