package com.erp.ecommerce.common.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @ToString
public class CustomerAccountResponseDTO implements Serializable {
    private UUID accountId;
}
