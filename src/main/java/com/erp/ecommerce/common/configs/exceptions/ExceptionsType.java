package com.erp.ecommerce.common.configs.exceptions;

public enum ExceptionsType {
    CREATE_ACCOUNT_ACTION_USERNAME_ALREADY_USED("5001"),
    CREATE_ACCOUNT_ACTION_EMAIL_ALREADY_USED("5002");
    private String code;
    ExceptionsType(String code) {
        this.code = code;
    }
}
