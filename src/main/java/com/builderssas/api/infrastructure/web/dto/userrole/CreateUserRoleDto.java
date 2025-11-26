package com.builderssas.api.infrastructure.web.dto.userrole;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación de una nueva asignación usuario-rol.
 *
 * Recibe únicamente los datos mínimos:
 * - userId
 * - roleId
 *
 * Otros campos (assignedAt, createdAt, updatedAt, active)
 * se inicializan en el caso de uso.
 */
public record CreateUserRoleDto(

        @NotNull(message = "El ID del usuario es obligatorio.")
        Long userId,

        @NotNull(message = "El ID del rol es obligatorio.")
        Long roleId

) {}
