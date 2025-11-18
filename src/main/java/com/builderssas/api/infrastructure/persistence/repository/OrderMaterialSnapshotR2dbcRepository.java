package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.OrderMaterialSnapshotEntity;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para la persistencia de los snapshots de materiales
 * generados durante la creación de una orden.
 *
 * Cada snapshot refleja un estado histórico INMUTABLE y se utiliza para:
 * - auditoría empresarial,
 * - reportes históricos,
 * - reconstrucción de órdenes,
 * - dashboards avanzados.
 */
@Repository
public interface OrderMaterialSnapshotR2dbcRepository
        extends ReactiveCrudRepository<OrderMaterialSnapshotEntity, Long> {

    /**
     * Obtiene todos los snapshots asociados a una orden.
     */
    Flux<OrderMaterialSnapshotEntity> findByOrderId(Long orderId);
}
