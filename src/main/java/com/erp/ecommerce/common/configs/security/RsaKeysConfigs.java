package com.erp.ecommerce.common.configs.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeysConfigs(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
