package com.builderssas.api.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO que recibe las credenciales del usuario para iniciar sesión.
 */
public record LoginDto(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String password

) {}
