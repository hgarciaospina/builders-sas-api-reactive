package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para asignar un rol a un usuario.
 *
 * Este puerto define la operación para crear una relación entre un usuario
 * y un rol dentro del sistema. No expone detalles de infraestructura y
 * trabaja únicamente con el modelo de dominio.
 */
public interface AssignUserRoleUseCase {

    /**
     * Asigna un rol a un usuario creando una nueva relación usuario-rol.
     *
     * @param record registro de dominio que contiene los datos de la asignación.
     * @return Mono con el registro creado.
     */
    Mono<UserRoleRecord> assign(UserRoleRecord record);
}
