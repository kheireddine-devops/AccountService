package com.erp.ecommerce.common.configs.exceptions.customs;

import com.erp.ecommerce.common.configs.exceptions.ExceptionsType;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractRuntimeException {

    public NotFoundException(ExceptionsType type) {
        super(type);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
