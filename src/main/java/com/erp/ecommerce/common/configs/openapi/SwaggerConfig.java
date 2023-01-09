package com.erp.ecommerce.common.configs.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name="api.swagger.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {
    @Value("${api.swagger.info.title}")  private String API_SWAGGER_INFO_TITLE;
    @Value("${api.swagger.info.description}") private String API_SWAGGER_INFO_DESCRIPTION;
    @Value("${api.swagger.info.version}") private String API_SWAGGER_INFO_VERSION;
    @Value("${api.swagger.info.base-package}") private String API_SWAGGER_INFO_BASE_PACKAGE;
    @Value("${api.swagger.info.group-name}") private String API_SWAGGER_INFO_GROUP_NAME;
    @Value("${api.swagger.info.contact.name}") private String API_SWAGGER_INFO_CONTACT_NAME;
    @Value("${api.swagger.info.contact.email}") private String API_SWAGGER_INFO_CONTACT_EMAIL;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(API_SWAGGER_INFO_GROUP_NAME)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(API_SWAGGER_INFO_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDate.class,java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class,java.util.Date.class)
                .apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(API_SWAGGER_INFO_TITLE)
                .description(API_SWAGGER_INFO_DESCRIPTION)
                .version(API_SWAGGER_INFO_VERSION)
//                .license("")
//                .licenseUrl("")
//                .termsOfServiceUrl("")
                .contact(new Contact(API_SWAGGER_INFO_CONTACT_NAME, null, API_SWAGGER_INFO_CONTACT_EMAIL))
                .build();
    }
}
