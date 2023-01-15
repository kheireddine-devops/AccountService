package com.erp.ecommerce.common.dtos.requests;

import com.erp.ecommerce.common.models.enums.GenderEnum;
import com.erp.ecommerce.common.validators.validations.Password.Password;
import com.erp.ecommerce.common.validators.validations.Password.PasswordType;
import com.erp.ecommerce.common.validators.validations.unique.email.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public interface AccountRequestsDTO {
        record CreateCustomerAccountDTO(
                @NotNull
                @NotBlank
                @Size(min = 2, max = 25)
                String firstname,
                @NotNull
                @NotBlank
                @Size(min = 2, max = 25)
                String lastname,
                @Past
                Date dateOfBirth,
                @NotNull
                GenderEnum gender,
                @NotNull
                @Email
                @UniqueEmail
                String email,
                @NotNull
                @NotBlank
                @Size(min = 4, max = 20)
                @Password(min = 6,max = 20,type = PasswordType.STRONG,message = "{validation.constraints.password.strong.message}")
                String password) {}

}
