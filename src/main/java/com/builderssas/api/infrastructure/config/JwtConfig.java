package com.builderssas.api.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración JWT centralizada.
 * Lee variables de entorno definidas en .env
 * No se comitea la clave secreta.
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {

    private String secretKey;
    private long accessTokenExpirationSeconds;
    private long refreshTokenExpirationSeconds;
}