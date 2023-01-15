package com.erp.ecommerce.common.actions.commands;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.models.enums.GenderEnum;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
import java.util.UUID;

public interface AccountCommands {
    record CreateAdminAccountCommand(@TargetAggregateIdentifier UUID adminId, String firstname, String lastname, GenderEnum gender, Date dateOfBirth, String email, String password) {
        public CreateAdminAccountCommand {
            adminId = UUID.randomUUID();
        }
    };

    record CreateCustomerAccountCommand(@TargetAggregateIdentifier UUID customerId, String firstname, String lastname, GenderEnum gender, Date dateOfBirth, String email, String password) {
        public CreateCustomerAccountCommand {
            customerId = UUID.randomUUID();
        }
    };
    record ActivateAccountCommand(@TargetAggregateIdentifier UUID accountId) {};
    record SendEmailVerificationCodeCommand(@TargetAggregateIdentifier UUID accountId, short verificationCode) {};
}
