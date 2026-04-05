package com.builderssas.api.domain.model.auth;

/**
 * Modelo de dominio que representa el resultado del proceso de recuperación de contraseña.
 *
 * Responsabilidad:
 * - Contener la información mínima necesaria para completar el flujo de recuperación
 * - Permitir a la capa de aplicación o web construir la respuesta al cliente
 *
 * No es un DTO web.
 * No contiene información de infraestructura.
 * No contiene tokens JWT.
 *
 * @param userId identificador del usuario
 * @param username nombre de usuario
 * @param fullName nombre completo del usuario
 * @param role rol principal del usuario
 * @param temporaryPassword contraseña temporal generada
 */
public record RecoverPasswordRecord(
        Long userId,
        String username,
        String fullName,
        String role,
        String temporaryPassword
) {
}