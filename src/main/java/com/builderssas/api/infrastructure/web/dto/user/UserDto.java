package com.builderssas.api.infrastructure.web.dto.user;

/**
 * DTO devuelto como respuesta al cliente para representar
 * información pública del usuario.
 *
 * No expone contraseñas ni datos sensibles.
 */
public record UserDto(
        Long id,
        String username,
        String displayName,
        String email,
        Boolean active
) {
}
