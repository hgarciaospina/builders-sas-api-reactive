package com.builderssas.api.domain.model.auth;

import java.time.LocalDateTime;

/**
 * Modelo de dominio para credenciales de autenticación.
 * No contiene lógica de negocio.
 */
public record AuthUserRecord(
        Long id,
        Long userId,
        String passwordHash,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
