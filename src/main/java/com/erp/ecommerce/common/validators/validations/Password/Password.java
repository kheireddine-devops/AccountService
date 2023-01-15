package com.erp.ecommerce.common.validators.validations.Password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    byte min() default 5;
    byte max() default 20;
    PasswordType type() default PasswordType.BLANK;
    String message() default "{validation.constraints.password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}