package com.erp.ecommerce.common.validators.validations.unique.email;

import com.erp.ecommerce.common.services.impls.AccountService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {
    private final AccountService accountService;
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        System.out.println("BLOCK INITIALIZE - UNIQUE-EMAIL VALIDATOR");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("BLOCK IS-VALID - UNIQUE-EMAIL VALIDATOR");
        if(value == null || value.isEmpty() || value.isBlank()) return false;
        return this.accountService.existsAccountByEmail(value);
    }
}
