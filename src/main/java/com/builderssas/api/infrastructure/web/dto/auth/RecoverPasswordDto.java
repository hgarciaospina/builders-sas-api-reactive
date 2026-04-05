package com.builderssas.api.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RecoverPasswordDto(
        @NotBlank(message = "El nombre del usuario es requerido")
        String username
) {}