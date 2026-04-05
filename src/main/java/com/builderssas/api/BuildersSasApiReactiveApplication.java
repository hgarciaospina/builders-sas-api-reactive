package com.builderssas.api;

import com.builderssas.api.infrastructure.config.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BuildersSasApiReactiveApplication {

    public static void main(String[] args) {

        // 🔥 CARGAR .env ANTES DE SPRING
        DotenvConfig.load();

        SpringApplication.run(BuildersSasApiReactiveApplication.class, args);
    }
}