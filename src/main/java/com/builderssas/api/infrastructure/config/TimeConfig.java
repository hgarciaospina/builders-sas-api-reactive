package com.builderssas.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

/**
 * Configuración global de tiempo para la aplicación.
 *
 * Define la zona horaria oficial usada por los casos de uso.
 */
@Configuration
public class TimeConfig {

    @Bean
    public ZoneId applicationZoneId() {
        return ZoneId.systemDefault();
    }
}
