package com.erp.ecommerce.common.dtos.requests;

import com.erp.ecommerce.common.configs.security.GrantTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @ToString
public class AuthRequestDTO implements Serializable {
    private GrantTypeEnum grantType = GrantTypeEnum.PASSWORD;
    private boolean withRefreshToken = false;
    private String refreshToken;
    private String username;
    private String password;
}
