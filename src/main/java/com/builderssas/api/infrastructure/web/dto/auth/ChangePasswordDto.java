package com.builderssas.api.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitar el cambio de contraseña.
 */
public record ChangePasswordDto(

        @NotNull(message = "El ID del usuario es obligatorio")
        Long userId,

        @NotBlank(message = "La contraseña actual no puede estar vacía")
        String oldPassword,

        @NotBlank(message = "La nueva contraseña no puede estar vacía")
        @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
        String newPassword

) {}
