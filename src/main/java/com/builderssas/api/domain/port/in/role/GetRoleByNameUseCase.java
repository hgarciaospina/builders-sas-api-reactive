package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener un rol por su nombre.
 *
 * Reglas:
 * - Se utiliza para validación de duplicados antes de crear o actualizar.
 * - Pertenece a la capa de dominio (puerto de entrada).
 * - No conoce infraestructura.
 */
public interface GetRoleByNameUseCase {

    /**
     * Busca un rol por su nombre.
     *
     * @param name nombre del rol
     * @return Mono con el RoleRecord si existe, o Mono.empty() si no existe
     */
    Mono<RoleRecord> findByName(String name);
}
