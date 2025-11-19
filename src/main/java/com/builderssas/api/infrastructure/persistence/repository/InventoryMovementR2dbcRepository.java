package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para la entidad InventoryMovementEntity.
 *
 * Esta interfaz pertenece exclusivamente a la capa de infraestructura
 * y define las consultas reactivas sobre la tabla inventory_movements.
 *
 * Es utilizada únicamente por los adaptadores de persistencia, quienes
 * implementan los puertos de dominio. Aquí NO va lógica de negocio.
 */
public interface InventoryMovementR2dbcRepository
        extends ReactiveCrudRepository<InventoryMovementEntity, Long> {

    /**
     * Obtiene los movimientos asociados a un material.
     *
     * @param materialId identificador del material
     * @return flujo reactivo de entidades InventoryMovementEntity
     */
    Flux<InventoryMovementEntity> findByMaterialId(Long materialId);

    /**
     * Obtiene los movimientos asociados a una orden específica.
     *
     * @param orderId identificador de la orden
     * @return flujo reactivo de entidades InventoryMovementEntity
     */
    Flux<InventoryMovementEntity> findByOrderId(Long orderId);

    /**
     * Obtiene los movimientos registrados por un usuario específico.
     *
     * Esta consulta permite obtener la trazabilidad completa
     * de los movimientos hechos por un usuario en el sistema.
     *
     * @param userId identificador del usuario
     * @return flujo reactivo de entidades InventoryMovementEntity
     */
    Flux<InventoryMovementEntity> findByUserId(Long userId);
}
