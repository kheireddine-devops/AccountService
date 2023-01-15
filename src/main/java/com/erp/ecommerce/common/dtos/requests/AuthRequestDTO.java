package com.erp.ecommerce.common.dtos.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AuthRequestDTO {
    record UsernamePassword(@NotNull @NotBlank String username, @NotNull @NotBlank String password, boolean withRefreshToken){}
    record RefreshToken(@NotNull @NotBlank String refreshToken){}
}