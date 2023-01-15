package com.erp.ecommerce.common.configs.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
//import springfox.documentation.builders.OAuthBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.ServerVariableBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.GrantType;
//import springfox.documentation.service.OAuth;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.service.Server;
//import springfox.documentation.service.ServerVariable;
//import springfox.documentation.service.TokenEndpoint;
//import springfox.documentation.service.TokenRequestEndpoint;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Configuration
//@ConditionalOnProperty(name="api.swagger.enable", havingValue = "true", matchIfMissing = false)
public class SwaggerConfig {
//    @Value("${api.swagger.info.title}")  private String API_SWAGGER_INFO_TITLE;
//    @Value("${api.swagger.info.description}") private String API_SWAGGER_INFO_DESCRIPTION;
//    @Value("${api.swagger.info.version}") private String API_SWAGGER_INFO_VERSION;
//    @Value("${api.swagger.info.base-package}") private String API_SWAGGER_INFO_BASE_PACKAGE;
//    @Value("${api.swagger.info.contact.name}") private String API_SWAGGER_INFO_CONTACT_NAME;
//    @Value("${api.swagger.info.contact.email}") private String API_SWAGGER_INFO_CONTACT_EMAIL;
//
//    private final String SECURITY_APIKEY_SCHEMA = "SECURITY_APIKEY_SCHEMA";
//    private final String SECURITY_OAUTH2_SCHEMA = "SECURITY_OAUTH2_SCHEMA";
//
//    @Bean
//    public Docket commandsApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("COMMANDS-SERVICE")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(API_SWAGGER_INFO_BASE_PACKAGE+".commands"))
//                .paths(PathSelectors.any())
//                .build()
//                .directModelSubstitute(LocalDate.class,java.sql.Date.class)
//                .directModelSubstitute(LocalDateTime.class,java.util.Date.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .securitySchemes(Collections.singletonList(getApiKeySecurityScheme()))
//                .securityContexts(Collections.singletonList(securityContext()))
//                .protocols(protocols())
//                .apiInfo(this.getApiInfo());
//    }
//
//    @Bean
//    public Docket queryApi() {
//
//        ServerVariable port = new ServerVariableBuilder()
//                .name("PORT")
//                .description("PORT")
//                .allowedValues(List.of("3000", "8080", "4200"))
//                .defaultValue("4200")
//                .extensions(Collections.emptyList())
//                .createServerVariable();
//        ServerVariable base = new ServerVariableBuilder()
//                .name("BASE")
//                .description("BASE URL")
//                .allowedValues(List.of("api", "rest"))
//                .defaultValue("rest")
//                .extensions(Collections.emptyList())
//                .createServerVariable();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("QUERY-SERVICE")
//                .select()
////                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage(API_SWAGGER_INFO_BASE_PACKAGE+".query"))
//                .paths(PathSelectors.any())
//                .build()
//                .directModelSubstitute(LocalDate.class,java.sql.Date.class)
//                .directModelSubstitute(LocalDateTime.class,java.util.Date.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .securitySchemes(List.of(getApiKeySecurityScheme(),this.getAuthSecurityScheme()))
//                .securityContexts(Collections.singletonList(securityContext()))
////                .servers(new Server("DEV SERVER","http://localhost:{PORT}/{BASE}","DEV ENV SERVER",List.of(port,base),List.of()))
//                .protocols(protocols())
////                .host("localhost:3000")
//                .apiInfo(this.getApiInfo());
//    }
//
//    private ApiInfo getApiInfo() {
//        return new ApiInfoBuilder()
//                .title(API_SWAGGER_INFO_TITLE)
//                .description(API_SWAGGER_INFO_DESCRIPTION)
//                .version(API_SWAGGER_INFO_VERSION)
////                .license("")
////                .licenseUrl("")
////                .termsOfServiceUrl("")
//                .contact(new Contact(API_SWAGGER_INFO_CONTACT_NAME, null, API_SWAGGER_INFO_CONTACT_EMAIL))
//                .build();
//    }
//
//
//    private ApiKey getApiKeySecurityScheme() {
//        return new ApiKey(SECURITY_APIKEY_SCHEMA, "Authorization", "header");
//    }
//
//    private OAuth getAuthSecurityScheme() {
//        GrantType grantType = new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint("/api/token", "oauthtoken"))
//                .tokenRequestEndpoint(new TokenRequestEndpoint("/api/authorize", "", ""))
//                .build();
//
//        OAuth oauth = new OAuthBuilder()
//                .name(SECURITY_OAUTH2_SCHEMA)
//                .grantTypes(Collections.singletonList(grantType))
//                .scopes(Arrays.asList(scopes()))
//                .build();
//        return oauth;
//    }
//
//    private AuthorizationScope[] scopes() {
//        return new AuthorizationScope[] {
//                new AuthorizationScope("read", "for read operations"),
//                new AuthorizationScope("write", "for write operations")
//        };
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        return List.of(new SecurityReference(SECURITY_APIKEY_SCHEMA, scopes()));
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
////                .forPaths(PathSelectors.regex("/api/**"))
//                .build();
//    }
//
//    private Set<String> protocols() {
//        Set<String> protocols = new HashSet<>();
//        protocols.add("http");
//        protocols.add("https");
//        return protocols;
//    }
}
