package com.builderssas.api.domain.port.in.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener snapshots asociados a una orden específica.
 */
public interface GetOrderMaterialSnapshotsByOrderIdUseCase {

    /**
     * Busca todos los snapshots asociados a una orden.
     * @param orderId ID de la orden
     * @return Flux de OrderMaterialSnapshotRecord
     */
    Flux<OrderMaterialSnapshotRecord> getByOrderId(Long orderId);
}
