package com.erp.ecommerce.commands.services.impls;


import com.erp.ecommerce.commands.services.specs.IAccountCommandsService;
import com.erp.ecommerce.common.actions.events.AccountEvents;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Admin;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import com.erp.ecommerce.common.repositories.AccountRepository;
import com.erp.ecommerce.common.services.specs.IAccountService;
import com.erp.ecommerce.common.utils.impls.MailMessagesUtils;
import com.erp.ecommerce.common.utils.specs.IMailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.annotation.MessageIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountCommandsService implements IAccountCommandsService {
    private final IAccountService accountService;
    private final IMailUtils mailUtils;

    @Override
    @EventHandler
    public void handle(AccountEvents.CustomerAccountCreatedEvent event) {
        log.info(event);
        Account account = new Account();
        account.setAccountId(event.customerId());
        account.setUsername(event.firstname()+"-"+event.lastname()+ RandomStringUtils.random(5, true, true)); //TODO GENERATE UNIQUE USERNAME
        account.setEmail(event.email());
        account.setPassword(event.password());
        account.setStatus(event.status());
        account.setRole(event.role());

        Customer customer = new Customer();
        customer.setFirstname(event.firstname());
        customer.setLastname(event.lastname());
        customer.setDateOfBirth(event.dateOfBirth());
        customer.setGender(event.gender());
        customer.setAccount(account);

        Customer savedCustomer = this.accountService.addCustomer(customer);

        log.info(savedCustomer);
    }

    @Override
    @EventHandler
//    public void handle(AccountEvents.AccountActivatedEvent event,@MessageIdentifier String messageIdentifier)
    public void handle(AccountEvents.AccountActivatedEvent event) {
        log.info(event);
        accountService.updateAccountStatus(event.accountId(), AccountStatusEnum.ACTIVATED);
    }

    @Override
    @EventHandler
    public void handle(AccountEvents.EmailVerificationCodeSentEvent event) {
        log.info(event);
        String email = this.accountService.getEmailByAccountId(event.accountId());
        if(email != null) {
            String content = MailMessagesUtils.confirmEmailAddress(event.verificationCode());
            boolean result = this.mailUtils.sendMail(email,"Please confirm your e-mail address",content);
            if(result) {
                log.info(this.accountService.updateAccountTempCodeValidateEmail(event.accountId(),event.verificationCode()));
            }
        }
    }
}
