package com.erp.ecommerce.common.configs.openapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.swagger")
public record SwaggerProperty(Info info,boolean enable) {

    public record Info(
            String title,
            String description,
            String version,
            String license,
            String licenseUrl,
            String termsOfServiceUrl,
            Contact contact,
            String basePackage,
            String groupName
    ){
        public record Contact(String email,String url, String name){}
    }
}
