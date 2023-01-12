package com.erp.ecommerce.common.validators.validations.username;

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
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    byte min() default 5;
    byte max() default 20;
    UsernameType type() default UsernameType.SIMPLE;
    String message() default "{validation.constraints.username.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
