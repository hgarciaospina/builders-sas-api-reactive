package com.builderssas.api.domain.model.auth;

import java.util.List;

/**
 * Modelo de dominio que representa al usuario autenticado en tiempo de ejecución.
 *
 * Responsabilidad:
 * - Transportar la identidad del usuario autenticado dentro del sistema
 * - Contener información necesaria para autorización (roles)
 *
 * Decisiones de diseño:
 * - Se utiliza una colección de roles para soportar múltiples permisos
 * - No contiene credenciales ni información sensible
 *
 * Este modelo:
 * - No conoce infraestructura
 * - No conoce HTTP
 *
 * @param userId identificador del usuario
 * @param username nombre de usuario
 * @param roles lista de roles asignados
 * @param fullName nombre completo del usuario
 */
public record AuthenticatedUserRecord(
        Long userId,
        String username,
        List<String> roles,
        String fullName
) {

    /**
     * Verifica si el usuario posee un rol específico.
     *
     * @param role rol a validar
     * @return true si el usuario tiene el rol
     */
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }
}