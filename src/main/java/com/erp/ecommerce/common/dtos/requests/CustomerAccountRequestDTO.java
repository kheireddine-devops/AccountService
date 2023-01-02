package com.erp.ecommerce.common.dtos.requests;

import com.erp.ecommerce.common.models.enums.GenderEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @ToString
public class CustomerAccountRequestDTO implements Serializable {
    private UUID customerId = UUID.randomUUID();
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private GenderEnum gender;
    private String email;
    private String password;
}
