package com.builderssas.api.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record CreateAuthUserDto(

        @NotNull(message = "El ID del usuario es obligatorio")
        Long userId,

        @NotBlank(message = "La contraseña es obligatoria")
        String password

) {}
