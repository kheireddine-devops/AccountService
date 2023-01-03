package com.erp.ecommerce.common.configs.exceptions;

import com.erp.ecommerce.common.configs.exceptions.customs.ConflictException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionEntity> handleConflictException(ConflictException exception, WebRequest request) {
        log.info("handleConflictException");
        ExceptionEntity<Boolean> exceptionEntity = new ExceptionEntity(exception.getType(),exception.getHttpStatus(),false);
        return new ResponseEntity<ExceptionEntity>(exceptionEntity,exceptionEntity.getStatus());
    }
}
