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
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null || value.isEmpty() || value.isBlank()) return false;
        return !this.accountService.existsAccountByEmail(value);
    }
}
