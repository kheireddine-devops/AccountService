package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAccountCommandsController;
import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.requests.CustomerAccountRequestDTO;
import com.erp.ecommerce.common.dtos.responses.AuthResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/accounts"})
@Log4j2
public class AccountCommandsController implements IAccountCommandsController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private AccountsMapper accountsMapper;
    @Override
    public CompletableFuture<String> addCustomer(CustomerAccountRequestDTO customerAccountRequestDTO) {
        AccountCommands.AddCustomerAccountCommand addCustomerAccountCommand = accountsMapper.fromCustomerAccountRequestDTOToAddCustomerAccountCommand(customerAccountRequestDTO);
        log.info(customerAccountRequestDTO);
        log.info(addCustomerAccountCommand);
        return this.commandGateway.send(addCustomerAccountCommand);
    }

    @PostMapping({"/auth"})
    public ResponseEntity<AuthResponseDTO> auth(Authentication authentication) {
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(","));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(30, ChronoUnit.DAYS))
                .issuer("EcommerceService")
                .claim("scope",scope)
                .build();

        String jwtAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setValid(true);
        authResponseDTO.setToken(jwtAccessToken);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
