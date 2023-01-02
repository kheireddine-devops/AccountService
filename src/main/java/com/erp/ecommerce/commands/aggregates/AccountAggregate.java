package com.erp.ecommerce.commands.aggregates;

import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.actions.events.AccountEvents;
import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Log4j2
public class AccountAggregate {
    @AggregateIdentifier
    private UUID accountAggregateId;
    protected AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AccountCommands.AddCustomerAccountCommand command) {
        log.info(command);
        this.accountAggregateId = command.customerId();
        AggregateLifecycle.apply(new AccountEvents.AddCustomerAccountEvent(
                command.customerId(),
                command.firstname(),
                command.lastname(),
                command.gender(),
                command.dateOfBirth(),
                command.email(),
                command.password(),
                AccountRoleEnum.ROLE_CUSTOMER,
                AccountStatusEnum.DEACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountEvents.AddCustomerAccountEvent event) {
        log.info(event);
        this.accountAggregateId = event.customerId();
    }
}
