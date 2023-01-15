package com.erp.ecommerce.commands.controllers.specs;

import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Account", description = "EndPoints for managing Accounts")
public interface IAccountCommandsController {

    @PostMapping({"/register/customer"})
    CompletableFuture<String> addCustomer(@RequestBody @Valid AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO);

    @GetMapping({"/infos/by-auth"})
    ResponseEntity<Map<String,Object>> getPrincipalInfos(Authentication authentication);

    @GetMapping({"/infos/by-token"})
    ResponseEntity<Map<String,Object>> getPrincipalInfos(JwtAuthenticationToken principal);

    @GetMapping({"/infos/by-jwt"})
    ResponseEntity<Map<String,Object>>  getPrincipalInfos(Jwt jwt);
}
