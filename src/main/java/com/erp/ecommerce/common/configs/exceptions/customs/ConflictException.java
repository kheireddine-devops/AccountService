package com.erp.ecommerce.common.configs.exceptions.customs;

import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import org.springframework.http.HttpStatus;

public class ConflictException extends AbstractRuntimeException {
    public ConflictException(ExceptionsType type) {
        super(type);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
