package com.erp.ecommerce.commands.services.impls;


import com.erp.ecommerce.commands.services.specs.IAccountCommandsService;
import com.erp.ecommerce.common.actions.events.AccountEvents;
import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.entities.Customer;
import com.erp.ecommerce.common.services.specs.IAccountService;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Log4j2
public class AccountCommandsService implements IAccountCommandsService {
    @Autowired
    private IAccountService accountService;
    @Override
    @EventHandler
    public void handle(AccountEvents.AddCustomerAccountEvent event) {
        log.info(event);
        Account account = new Account();
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
}
