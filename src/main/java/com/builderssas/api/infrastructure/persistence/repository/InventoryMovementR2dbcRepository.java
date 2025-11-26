package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para InventoryMovementEntity.
 * Define las operaciones reactivas sobre la tabla inventory_movements.
 * No contiene lógica de negocio; únicamente consultas directas.
 */
public interface InventoryMovementR2dbcRepository
        extends ReactiveCrudRepository<InventoryMovementEntity, Long> {

    Flux<InventoryMovementEntity> findByMaterialId(Long materialId);

    Flux<InventoryMovementEntity> findByOrderId(Long orderId);

    Flux<InventoryMovementEntity> findByUserId(Long userId);

    /**
     * Devuelve el último movimiento registrado para un material,
     * ordenado por fecha descendente. Fundamental para obtener
     * el stock actual sin ordenar en memoria.
     */
    @Query("""
        SELECT *
        FROM inventory_movements
        WHERE material_id = :materialId
        ORDER BY movement_date DESC
        LIMIT 1
        """)
    Mono<InventoryMovementEntity> findLastByMaterialId(Long materialId);
}
