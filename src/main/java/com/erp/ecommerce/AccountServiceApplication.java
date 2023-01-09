package com.erp.ecommerce;

import com.erp.ecommerce.common.configs.jpa.AuditorAwareImpl;
import com.erp.ecommerce.common.configs.openapi.SwaggerProperty;
import com.erp.ecommerce.common.configs.security.JwtProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableConfigurationProperties({JwtProperty.class, SwaggerProperty.class})
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    AuditorAware<UUID> auditorAware() {
        return new AuditorAwareImpl();
    }
}
