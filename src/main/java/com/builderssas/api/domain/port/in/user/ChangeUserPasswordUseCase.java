package com.builderssas.api.domain.port.in.user;

import reactor.core.publisher.Mono;

/**
 * Caso de uso para cambiar la contraseña de un usuario dentro del sistema.
 *
 * Este caso de uso forma parte de la capa de Aplicación en la
 * Arquitectura Hexagonal. Define la operación necesaria para
 * actualizar la contraseña de manera segura, sin exponer detalles
 * de implementación ni de persistencia.
 *
 * Responsabilidades:
 *  • Verificar que el usuario exista.
 *  • Validar la contraseña actual (si aplica según la política).
 *  • Validar la fortaleza de la nueva contraseña.
 *  • Delegar el cifrado de la contraseña a la infraestructura.
 *  • Persistir la nueva contraseña de forma reactiva.
 *
 * Notas:
 *  • La verificación/cifrado NO se define aquí; se implementa en la capa de infraestructura.
 *  • La entrada y salida son completamente reactivas (Mono<Void>).
 *  • No retorna contraseñas ni datos sensibles en ningún momento.
 */
public interface ChangeUserPasswordUseCase {

    /**
     * Cambia la contraseña del usuario.
     *
     * @param userId          ID del usuario al que se le cambiará la contraseña.
     * @param currentPassword Contraseña actual (opcional según reglas de negocio).
     * @param newPassword     Nueva contraseña.
     *
     * @return Mono<Void> indicando que el proceso finalizó correctamente.
     */
    Mono<Void> changePassword(Long userId, String currentPassword, String newPassword);
}
