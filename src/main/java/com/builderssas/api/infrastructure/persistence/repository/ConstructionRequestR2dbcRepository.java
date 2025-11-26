package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionRequestEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para la entidad ConstructionRequestEntity.
 *
 * Esta interfaz se apoya en ReactiveCrudRepository para operaciones básicas
 * y declara métodos adicionales que permiten consultas basadas en criterios.
 *
 * Spring Data genera automáticamente las implementaciones a partir del
 * nombre de los métodos según sus convenciones.
 */
public interface ConstructionRequestR2dbcRepository
        extends ReactiveCrudRepository<ConstructionRequestEntity, Long> {

    /**
     * Obtiene todas las solicitudes registradas por un usuario específico.
     *
     * @param requestedByUserId identificador del usuario solicitante
     * @return Flux con las solicitudes encontradas
     */
    Flux<ConstructionRequestEntity> findByRequestedByUserId(Long requestedByUserId);

    /**
     * Obtiene todas las solicitudes asociadas a un proyecto.
     *
     * @param projectId identificador del proyecto
     * @return Flux con las solicitudes encontradas
     */
    Flux<ConstructionRequestEntity> findByProjectId(Long projectId);
}
