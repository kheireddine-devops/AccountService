package com.erp.ecommerce.commands.aggregates;

import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.actions.events.AccountEvents;
import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import com.erp.ecommerce.common.configs.exceptions.customs.ConflictException;
import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.services.specs.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.NumberUtils;

import java.util.UUID;

@Aggregate
@Log4j2
public class AccountAggregate {
    @AggregateIdentifier
    private UUID accountAggregateId;

    protected AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AccountCommands.CreateCustomerAccountCommand command/*,IAccountService accountService*/) {
        log.info(command);
//        boolean existEmail = accountService.existsAccountByEmail(command.email());
//        if(existEmail) throw new ConflictException(ExceptionsType.CREATE_ACCOUNT_ACTION_EMAIL_ALREADY_USED);

        AggregateLifecycle.apply(new AccountEvents.CustomerAccountCreatedEvent(
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
    public void on(AccountEvents.CustomerAccountCreatedEvent event) {
        log.info(event);
        this.accountAggregateId = event.customerId();
        AggregateLifecycle.apply(new AccountEvents.AccountActivatedEvent(event.customerId()));
    }

    @EventSourcingHandler
    public void on(AccountEvents.AccountActivatedEvent event) {
        log.info(event);
        AggregateLifecycle.apply(new AccountEvents.EmailVerificationCodeSentEvent(
                event.accountId(),
                NumberUtils.parseNumber(RandomStringUtils.random(6,false, true), Integer.class)
        ));
    }

    @EventSourcingHandler
    public void on(AccountEvents.EmailVerificationCodeSentEvent event) {
        log.info(event);
    }
}
