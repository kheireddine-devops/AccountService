package com.erp.ecommerce.common.configs.security;

public class EndPointsConfigs {
    public static final String[] AUTH_WHITELIST = {
            /* **************** Guests EndPoints *************** */
            "/accounts/auth",
            "/accounts/auth/refresh-token",
            "/accounts/register/{account-type}",
            /* *************** Dev EndPoints *************** */
            "/dev/event-store/{aggregate-id}",
            "/dev/**",
            /* *************** Swagger UI v2 *************** */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            //* *************** Swagger UI v3 (OpenAPI) *************** */
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
}
