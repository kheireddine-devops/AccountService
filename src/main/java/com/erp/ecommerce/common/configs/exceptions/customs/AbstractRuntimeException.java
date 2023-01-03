package com.erp.ecommerce.common.configs.exceptions.customs;

import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter @AllArgsConstructor
public abstract class AbstractRuntimeException extends RuntimeException {
    private ExceptionsType type;
    public abstract HttpStatus getHttpStatus();
}
