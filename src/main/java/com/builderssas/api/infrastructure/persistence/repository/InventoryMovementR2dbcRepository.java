package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para la persistencia de movimientos de inventario.
 *
 * Esta interfaz permite almacenar, consultar y auditar todos los
 * movimientos registrados en el sistema.
 *
 * La responsabilidad del repositorio es estrictamente acceso a datos;
 * la lógica de negocio se ejecuta en los casos de uso.
 */
@Repository
public interface InventoryMovementR2dbcRepository
        extends ReactiveCrudRepository<InventoryMovementEntity, Long> {

    /**
     * Obtiene todos los movimientos asociados a un material.
     */
    Flux<InventoryMovementEntity> findByMaterialId(Long materialId);

    /**
     * Obtiene todos los movimientos asociados a una orden.
     */
    Flux<InventoryMovementEntity> findByOrderId(Long orderId);

    /**
     * Obtiene todos los movimientos realizados por un usuario específico.
     */
    Flux<InventoryMovementEntity> findByUserId(Long userId);

    /**
     * Busca movimientos por fecha exacta.
     */
    Flux<InventoryMovementEntity> findByMovementDateBetween(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );

    /**
     * Permite auditorías detalladas por rol del usuario.
     */
    Flux<InventoryMovementEntity> findByUserRole(String userRole);
}
