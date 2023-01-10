package com.erp.ecommerce.common.configs.openapi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${api.swagger.info.title}")  private String API_SWAGGER_INFO_TITLE;
    @Value("${api.swagger.info.description}") private String API_SWAGGER_INFO_DESCRIPTION;
    @Value("${api.swagger.info.version}") private String API_SWAGGER_INFO_VERSION;
    @Value("${api.swagger.info.base-package}") private String API_SWAGGER_INFO_BASE_PACKAGE;
    @Value("${api.swagger.info.contact.name}") private String API_SWAGGER_INFO_CONTACT_NAME;
    @Value("${api.swagger.info.contact.email}") private String API_SWAGGER_INFO_CONTACT_EMAIL;

    private final String SECURITY_BASIC_SCHEMA = "SECURITY_BASIC_SCHEMA";
    private final String SECURITY_APIKEY_SCHEMA = "SECURITY_APIKEY_SCHEMA";
    private final String SECURITY_JWT_SCHEMA = "SECURITY_JWT_SCHEMA";
    private final String SECURITY_OAUTH2_SCHEMA = "SECURITY_OAUTH2_SCHEMA";

    @Bean
    public GroupedOpenApi queryApi() {
        return GroupedOpenApi.builder()
                .group("QUERY-SERVICE")
                .pathsToMatch("/accounts/**")
                .packagesToScan("com.erp.ecommerce.query")
                .packagesToExclude("com.erp.ecommerce.commands")
                .build();
    }

    @Bean
    public GroupedOpenApi commandsApi() {
        return GroupedOpenApi.builder()
                .group("COMMANDS-SERVICE")
                .pathsToMatch("/accounts/**")
                .pathsToExclude("/dev/**")
                .packagesToScan("com.erp.ecommerce.commands")
                .packagesToExclude("com.erp.ecommerce.query")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(this.info())
                .servers(this.servers())
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_BASIC_SCHEMA))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_JWT_SCHEMA))
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_APIKEY_SCHEMA))
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_OAUTH2_SCHEMA,List.of("private","public")))
                .components(this.components());
    }

    private Components components() {
        SecurityScheme ApiKeySecurityScheme = new SecurityScheme().name(SECURITY_APIKEY_SCHEMA).type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("Authorization").bearerFormat("JWT");
        SecurityScheme JwtSecurityScheme = new SecurityScheme().name(SECURITY_JWT_SCHEMA).type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT");
        SecurityScheme BasicSecurityScheme = new SecurityScheme().name(SECURITY_BASIC_SCHEMA).type(SecurityScheme.Type.HTTP).scheme("basic");
        SecurityScheme OAuth2securityScheme = new SecurityScheme().name(SECURITY_OAUTH2_SCHEMA).type(SecurityScheme.Type.OAUTH2).flows(new OAuthFlows()
                .clientCredentials(new OAuthFlow()
                        .tokenUrl("http://localhost:3000/oauth/token")
                        .refreshUrl("http://localhost:3000/oauth/refresh")
                        .scopes(new Scopes()
                                .addString("read", "for read operations")
                                .addString("write", "for write operations")
                        )));

        return new Components()
//                .addSecuritySchemes(SECURITY_BASIC_SCHEMA,BasicSecurityScheme);
//                .addSecuritySchemes(SECURITY_APIKEY_SCHEMA,ApiKeySecurityScheme);
                .addSecuritySchemes(SECURITY_JWT_SCHEMA,JwtSecurityScheme);
//                .addSecuritySchemes(SECURITY_OAUTH2_SCHEMA,OAuth2securityScheme);
    }

    private Info info() {
        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        return new Info()
                .title(API_SWAGGER_INFO_TITLE)
                .description(API_SWAGGER_INFO_DESCRIPTION)
                .version(API_SWAGGER_INFO_VERSION)
                .license(mitLicense)
                .contact(this.contact());
    }

    private Contact contact() {
        return new Contact()
                .name(API_SWAGGER_INFO_CONTACT_NAME)
                .email(API_SWAGGER_INFO_CONTACT_EMAIL);
    }

    private List<Server> servers() {
        ServerVariables variables = new ServerVariables();
        variables.addServerVariable("PROTOCOL",new ServerVariable()
                .description("PROTOCOL VARIABLE")
                .addEnumItem("http")
                .addEnumItem("https")
                ._default("http")
        );
        variables.addServerVariable("PORT",new ServerVariable()
                .description("PORT VARIABLE")
                .addEnumItem("8080")
                .addEnumItem("3000")
                ._default("3000")
        );
        variables.addServerVariable("BASE",new ServerVariable()
                .description("BASE VARIABLE")
                .addEnumItem("api")
                .addEnumItem("rest")
                ._default("api")
        );

        return List.of(
                new Server().description("Server URL in Local environment").url("{PROTOCOL}://localhost:{PORT}/{BASE}").variables(variables),
                new Server().description("Server URL in Production environment").url("{PROTOCOL}://ecommerce.com:{PORT}/{BASE}").variables(variables)
        );
    }
}
