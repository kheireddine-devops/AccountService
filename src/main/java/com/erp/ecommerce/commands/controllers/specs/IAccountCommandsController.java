package com.erp.ecommerce.commands.controllers.specs;

import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import com.erp.ecommerce.common.dtos.requests.AuthRequestDTO;
import com.erp.ecommerce.common.dtos.responses.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IAccountCommandsController {
    @PostMapping({"/basic/auth"})
    ResponseEntity<AuthResponseDTO> auth(Authentication authentication);

    @PostMapping({"/auth"})
    ResponseEntity<AuthResponseDTO> auth(@RequestBody AuthRequestDTO authRequestDTO);

    @GetMapping({"/profile"})
    ResponseEntity<Map<String,Object>> currenUser(Authentication authentication);
    @PostMapping({"/customers"})
    CompletableFuture<String> addCustomer(@RequestBody AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO);

}
