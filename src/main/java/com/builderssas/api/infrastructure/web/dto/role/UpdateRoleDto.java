package com.builderssas.api.infrastructure.web.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO utilizado para actualizar la información de un rol existente.
 *
 * Incluye validaciones para garantizar la integridad de los datos de entrada.
 *
 * Este DTO no contiene lógica de negocio.
 */
public record UpdateRoleDto(

        @NotNull(message = "El rol es obligatorio")
        @Positive(message = "El  debe ser un número positivo")
        Long id,

        @NotBlank(message = "El nombre del rol es obligatorio")
        String name,

        @NotBlank(message = "La descripción del rol es obligatoria")
        String description

) {}
