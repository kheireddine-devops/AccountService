package com.erp.ecommerce.commands.controllers.specs;

import com.erp.ecommerce.common.dtos.requests.AuthRequestDTO;
import com.erp.ecommerce.common.dtos.responses.AuthResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Account", description = "Authentication Endpoints")
public interface IAuthController {
    @PostMapping({"/basic/auth"})
    ResponseEntity<AuthResponseDTO> auth(Authentication authentication);

    @PostMapping({"/auth"})
    ResponseEntity<AuthResponseDTO> auth(@RequestBody @Valid AuthRequestDTO.UsernamePassword authRequestDTO);

    @PostMapping({"/auth/refresh-token"})
    ResponseEntity<AuthResponseDTO> auth(@RequestBody @Valid AuthRequestDTO.RefreshToken authRequestDTO);
}
