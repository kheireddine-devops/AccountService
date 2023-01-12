package com.erp.ecommerce.commands.services.specs;

import com.erp.ecommerce.common.actions.events.AccountEvents;

public interface IAccountCommandsService {
    void handle(AccountEvents.CustomerAccountCreatedEvent event);
    void handle(AccountEvents.AccountActivatedEvent event);
    void handle(AccountEvents.EmailVerificationCodeSentEvent event);
}
