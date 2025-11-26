package com.builderssas.api.infrastructure.web.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de roles.
 *
 * Reglas:
 * - name: obligatorio, no nulo, no vacío
 * - description: obligatorio, no nulo, no vacío
 *
 * El campo "active" NO se solicita: siempre se crea en TRUE.
 */
public record CreateRoleDto(

        @NotBlank(message = "El nombre del rol es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String name,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 2, max = 200, message = "La descripción debe tener entre 2 y 200 caracteres")
        String description
) {}
