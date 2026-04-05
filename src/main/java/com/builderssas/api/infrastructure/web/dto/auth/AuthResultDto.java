package com.builderssas.api.infrastructure.web.dto.auth;

import java.util.List;

/**
 * DTO de respuesta para autenticación.
 *
 * Representa la respuesta HTTP del proceso de autenticación.
 *
 * Responsabilidad:
 * - Transportar datos hacia el cliente
 * - Adaptarse al modelo de dominio sin contener lógica de negocio
 *
 * Características:
 * - Soporta múltiples roles
 * - Incluye tokens ACCESS y REFRESH
 * - Indica estado de autenticación
 *
 * No contiene lógica de negocio.
 */
public record AuthResultDto(
        Long userId,
        String username,
        String displayName,
        List<String> roles,
        Boolean authenticated,
        String tempPassword,
        String accessToken,
        String refreshToken
) {}