package com.builderssas.api.domain.port.in.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todos los snapshots registrados.
 */
public interface GetAllOrderMaterialSnapshotsUseCase {

    /**
     * Obtiene todos los snapshots almacenados.
     * @return Flux de OrderMaterialSnapshotRecord
     */
    Flux<OrderMaterialSnapshotRecord> getAll();
}
