package com.erp.ecommerce.common.configs.security;

public class EndPointsConfigs {
    public static final String[] AUTH_WHITELIST = {
            /* **************** Guests EndPoints *************** */
            "/accounts/auth",
            "/accounts/auth/refresh-token",
            "/accounts/register/{account-type}",
            /* *************** Dev EndPoints *************** */
            "/dev/event-store/{aggregate-id}",
            "/dev/**"
    };
}
