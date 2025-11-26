package com.builderssas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BuildersSasApiReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildersSasApiReactiveApplication.class, args);
    }
}
