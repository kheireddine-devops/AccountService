package com.erp.ecommerce.common.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @ToString
public class AuthResponseDTO implements Serializable {
    private boolean valid = false;
    private String accessToken;
    private String refreshToken;
}
