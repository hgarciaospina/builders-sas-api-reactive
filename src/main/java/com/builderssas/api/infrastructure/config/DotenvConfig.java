package com.builderssas.api.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Bootstrap de variables de entorno.
 *
 * Se ejecuta ANTES de Spring Boot.
 * Inyecta variables del .env en System Properties.
 *
 * ✔ Soluciona placeholders ${JWT_*}
 * ✔ Compatible con producción
 * ✔ No rompe arquitectura hexagonal
 */
public final class DotenvConfig {
    private DotenvConfig() {}
    public static  void load() {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}