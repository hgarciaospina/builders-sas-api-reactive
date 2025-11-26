package com.builderssas.api.infrastructure.web.dto.userrole;

import java.time.LocalDateTime;

/**
 * DTO de salida para exponer información de una relación usuario-rol.
 *
 * Representa exactamente los valores del dominio,
 * pero aislando el record UserRoleRecord de la capa Web.
 */
public record UserRoleDto(
        Long id,
        Long userId,
        Long roleId,
        LocalDateTime assignedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean active
) {}
