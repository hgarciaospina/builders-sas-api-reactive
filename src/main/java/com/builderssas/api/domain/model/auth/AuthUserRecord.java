package com.builderssas.api.domain.model.auth;

import java.time.LocalDateTime;

/**
 * Modelo de dominio inmutable que representa las credenciales de autenticación de un usuario.
 *
 * Responsabilidad:
 * - Representar el estado de autenticación de un usuario dentro del dominio
 * - Proveer la información necesaria para validar procesos de login
 *
 * Decisiones de diseño:
 * - Se utiliza "boolean" en lugar de "Boolean" para evitar estados nulos
 * - La propiedad "active" representa un estado binario válido del dominio
 *
 * Este modelo:
 * - No contiene lógica de negocio compleja
 * - No conoce infraestructura
 * - No conoce HTTP ni mecanismos de transporte
 *
 * @param id identificador del registro de autenticación
 * @param userId identificador del usuario asociado
 * @param passwordHash hash seguro de la contraseña
 * @param active indica si la cuenta está habilitada para autenticación
 * @param createdAt fecha de creación del registro
 * @param updatedAt fecha de última actualización
 */
public record AuthUserRecord(
        Long id,
        Long userId,
        String passwordHash,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    /**
     * Indica si el usuario puede autenticarse en el sistema.
     *
     * @return true si la cuenta está activa, false en caso contrario
     */
    public boolean isActive() {
        return active;
    }
}