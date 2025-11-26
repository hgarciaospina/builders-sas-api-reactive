package com.builderssas.api.domain.port.in.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso interno para crear snapshots de manera automática
 * durante la generación de una orden de construcción.
 */
public interface CreateOrderMaterialSnapshotUseCase {

    /**
     * Crea un snapshot de materiales.
     * @param snapshot dominio del snapshot
     * @return Mono con el snapshot persistido
     */
    Mono<OrderMaterialSnapshotRecord> create(OrderMaterialSnapshotRecord snapshot);
}
