package com.builderssas.api.domain.model.auth;

/**
 * Resultado del caso de uso de autenticación.
 *
 * Contiene:
 * - Usuario autenticado
 * - Token de acceso (ACCESS)
 * - Token de refresco (REFRESH)
 *
 * Este modelo:
 * - Pertenece al dominio
 * - No es un DTO web
 * - No contiene lógica de infraestructura
 */
public record AuthSessionRecord(
        AuthenticatedUserRecord user,
        String accessToken,
        String refreshToken
) {
}