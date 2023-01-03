package com.erp.ecommerce.common.configs.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class ExceptionEntity<T> implements Serializable {
    private HttpStatus status;
    private ExceptionsType type;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd:MM:yyyy hh:mm:ss")
    private Date timestamp;

    private ExceptionEntity() {}

    public ExceptionEntity(ExceptionsType type, HttpStatus status, T data) {
        this.setType(type);
        this.setStatus(status);
        this.setData(data);
        this.setTimestamp(new Date());
    }
}
