package com.erp.ecommerce.common.validators.validations.username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username,String> {
    private UsernameType type;
    private byte min = 0;
    private byte max = 20;
    private String patternSelected = null;
    private Pattern pattern;

    @Override
    public void initialize(Username constraintAnnotation) {
        this.type = constraintAnnotation.type();

        if( (constraintAnnotation.min() > 0) && (constraintAnnotation.min() < Byte.MAX_VALUE)) {
            this.min = constraintAnnotation.min();
        } else {
            throw new RuntimeException("Username Validator - min Not Valid");
        }

        if( (constraintAnnotation.max() > 0) &&
            (constraintAnnotation.min() < Byte.MAX_VALUE) &&
            (constraintAnnotation.max() > this.min) ) {
            this.max = constraintAnnotation.max();
        } else {
            throw new RuntimeException("UERNAME max Not Valid");
        }

        /*
            Strict Username requirements :
              ^[a-zA-Z0-9]      # start with an alphanumeric character
              (                 # start of (group 1)
                [._-](?![._-])  # follow by a dot, hyphen, or underscore, negative lookahead to
                                # ensures dot, hyphen, and underscore does not appear consecutively
                |               # or
                [a-zA-Z0-9]     # an alphanumeric character
              )                 # end of (group 1)
              {3,18}            # ensures the length of (group 1) between 3 and 18 or (max - 2)
              [a-zA-Z0-9]$      # end with an alphanumeric character

                                # {3,18} plus the first and last alphanumeric characters,
                                # total length became {min - 2,max - 2}
         */

        System.out.println("BLOCK INITIALIZE - USERNAME VALIDATOR");

        patternSelected = switch (this.type) {
            case SIMPLE -> String.format("^[a-z0-9\\._-]{%d,%d}$",this.min,this.max);
            case STRICT -> String.format("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){%d,%d}[a-zA-Z0-9]$",Math.max(3,this.min-2),this.max-2);
            default -> throw new RuntimeException("PATTERN VALIDATOR NOT SELECTED");
        };

        pattern = Pattern.compile(this.patternSelected);

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("BLOCK IS-VALID - USERNAME VALIDATOR");
        if(value == null || value.isEmpty() || value.isBlank()) return false;
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
