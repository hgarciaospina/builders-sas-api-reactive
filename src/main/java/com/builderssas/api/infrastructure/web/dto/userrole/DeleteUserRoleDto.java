package com.builderssas.api.infrastructure.web.dto.userrole;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para realizar operación de borrado lógico (soft delete)
 * sobre una relación usuario-rol.
 *
 * Solo se requiere el ID de la asignación.
 */
public record DeleteUserRoleDto(

        @NotNull(message = "El ID del registro a eliminar es obligatorio.")
        Long id

) {}
