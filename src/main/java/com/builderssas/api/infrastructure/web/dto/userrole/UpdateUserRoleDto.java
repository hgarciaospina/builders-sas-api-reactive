package com.builderssas.api.infrastructure.web.dto.userrole;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar una asignación usuario-rol.
 *
 * Por políticas de integridad:
 * - NO permite modificar userId
 * - NO permite modificar roleId
 *
 * Únicamente se actualiza el estado "active".
 */
public record UpdateUserRoleDto(

        @NotNull(message = "El ID del registro a actualizar es obligatorio.")
        Long id,

        @NotNull(message = "El estado 'active' es obligatorio.")
        Boolean active

) {}
