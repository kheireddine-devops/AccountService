package com.erp.ecommerce.common.dtos.requests;

import com.erp.ecommerce.common.models.enums.GenderEnum;

import java.util.Date;

public interface AccountRequestsDTO {
        record CreateCustomerAccountDTO(String firstname, String lastname, Date dateOfBirth, GenderEnum gender, String email, String password) {}

}
