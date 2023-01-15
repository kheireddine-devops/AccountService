package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAccountCommandsController;
import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/accounts"})
@Log4j2
@RequiredArgsConstructor
public class AccountCommandsController implements IAccountCommandsController {
    private final CommandGateway commandGateway;
    private final AccountsMapper accountsMapper;

    @Override
    public CompletableFuture<String> addCustomer(AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO) {
        AccountCommands.CreateCustomerAccountCommand createCustomerAccountCommand = accountsMapper.fromAddCustomerAccountDTOToCreateCustomerAccountCommand(createCustomerAccountDTO);
        return this.commandGateway.send(createCustomerAccountCommand);
    }

    @Override
    public ResponseEntity<Map<String,Object>> getPrincipalInfos(Authentication authentication) {
        Collection<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Map<String,Object> infos = Map.of(
                "username", authentication.getName(),
                "authorities", authorities,
                "details",authentication.getDetails()
//                "principal",authentication.getPrincipal(),
//                "credentials",authentication.getCredentials()
        );
        return new ResponseEntity<>(infos,HttpStatus.OK);
    }

    public ResponseEntity<Map<String,Object>> getPrincipalInfos(JwtAuthenticationToken principal) {
        Collection<String> authorities = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String,Object> infos = Map.of(
                "username", principal.getName(),
                "authorities ",  authorities,
                "details", principal.getDetails(),
//                "token", principal.getToken(),
//                "credentials", principal.getCredentials(),
                "tokenAttributes", principal.getTokenAttributes()
        );
        return new ResponseEntity<>(infos,HttpStatus.OK);
    }

    public ResponseEntity<Map<String,Object>> getPrincipalInfos(@AuthenticationPrincipal Jwt jwt) {
        Map<String,Object> infos = Map.of("jwt",jwt);
        return new ResponseEntity<>(infos, HttpStatus.OK);
    }
}
