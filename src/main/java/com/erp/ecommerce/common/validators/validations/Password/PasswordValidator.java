package com.erp.ecommerce.common.validators.validations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    private Pattern pattern = null;

    @Override
    public void initialize(Password constraintAnnotation) {
        byte min = 0;
        byte max = 0;
        if( (constraintAnnotation.min() > 0) && (constraintAnnotation.min() < Byte.MAX_VALUE)) {
            min = constraintAnnotation.min();
        } else {
            throw new RuntimeException("Password Validator - MIN Invalid");
        }

        if( (constraintAnnotation.max() > 0) &&
                (constraintAnnotation.max() < Byte.MAX_VALUE) &&
                (constraintAnnotation.max() >= min) ) {
            max = constraintAnnotation.max();
        } else {
            throw new RuntimeException("Password Validator - MAX Invalid");
        }

         /*
            Blank Password requirements :
                1 - Password must contain at least one lowercase or uppercase Latin character [a-zA-Z].
            Very Weak Password requirements :
                1 - Password must contain at least one digit [0-9] or one lowercase or uppercase Latin character [a-zA-Z].
            Weak Password requirements :
                1 - Password must contain at least one digit [0-9].
                2 - Password must contain at least one lowercase or uppercase Latin character [a-zA-Z].
            Medium Password requirements :
                1 - Password must contain at least one digit [0-9].
                2 - Password must contain at least one lowercase Latin character [a-z].
                3 - Password must contain at least one uppercase Latin character [A-Z].
            Strong Password requirements :
                1 - Password must contain at least one digit [0-9].
                2 - Password must contain at least one lowercase Latin character [a-z].
                3 - Password must contain at least one uppercase Latin character [A-Z].
                4 - Password must contain at least one special character like ! @ # & ( ).
         */

        String patternSelected = switch (constraintAnnotation.type()) {
            case BLANK -> String.format("^(?=.*[a-z-A-Z]).{%d,%d}$",min,max);
            case VERY_WEAK -> String.format("^(?=.*[0-9a-z-A-Z]).{%d,%d}$",min,max);
            case WEAK -> String.format("^(?=.*[0-9])(?=.*[a-zA-Z]).{%d,%d}$",min,max);
            case MEDIUM -> String.format("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{%d,%d}$",min, max);
            case STRONG -> String.format("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{%d,%d}$",min,max);
            case VERY_STRONG -> throw new RuntimeException("IMPLEMENTATION NOT FOUND");
            default -> throw new RuntimeException("PATTERN NOT SELECTED");
        };

        this.pattern = Pattern.compile(patternSelected);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty() || value.isBlank()) return false;
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
