package com.erp.ecommerce.commands.services.specs;

import com.erp.ecommerce.common.actions.events.AccountEvents;

public interface IAccountCommandsService {
    void handle(AccountEvents.AddCustomerAccountEvent event);
}
