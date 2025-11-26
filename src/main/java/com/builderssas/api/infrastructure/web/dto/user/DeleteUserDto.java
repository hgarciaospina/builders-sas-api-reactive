package com.builderssas.api.infrastructure.web.dto.user;

import jakarta.validation.constraints.NotNull;

/**
 * DTO utilizado para realizar un borrado lógico (soft delete) del usuario.
 *
 * Regla del proyecto:
 * - El identificador se envía en el cuerpo, no en la URL.
 *
 * El campo "reason" es opcional y permite documentar auditoría de la operación.
 */
public record DeleteUserDto(

        @NotNull(message = "El identificador del usuario es obligatorio.")
        Long id,

        String reason
) {
}
