package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo para operaciones de persistencia relacionadas
 * con órdenes de construcción. Esta interfaz expone métodos derivados
 * que permiten obtener tanto el historial completo de un proyecto como
 * la última orden registrada según la fecha de finalización programada.
 *
 * Todos los métodos devuelven tipos reactivos (Mono/Flux), cumpliendo
 * con el modelo no bloqueante requerido por R2DBC y WebFlux.
 */
public interface ConstructionOrderR2dbcRepository
        extends ReactiveCrudRepository<ConstructionOrderEntity, Long> {

    /**
     * Obtiene todas las órdenes de construcción asociadas a un proyecto.
     *
     * @param projectId identificador del proyecto
     * @return flujo con todas las órdenes encontradas
     */
    Flux<ConstructionOrderEntity> findAllByProjectId(Long projectId);

    /**
     * Obtiene la última orden registrada para un proyecto, tomando como
     * criterio la fecha de finalización programada más reciente.
     *
     * @param projectId identificador del proyecto
     * @return orden más reciente o Mono vacío si no existen órdenes
     */
    Mono<ConstructionOrderEntity> findTopByProjectIdOrderByScheduledEndDateDesc(Long projectId);

    Mono<Boolean> existsByLatitudeAndLongitude(Double latitude, Double longitude);

    @Query("""
    SELECT *
    FROM construction_orders
    WHERE project_id = :projectId
    ORDER BY end_date DESC
    LIMIT 1
""")
    Mono<ConstructionOrderEntity> findLastByProjectId(Long projectId);
}
