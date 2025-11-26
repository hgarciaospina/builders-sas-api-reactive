package com.builderssas.api.application.usecase.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.domain.port.in.ordermaterialsnapshot.GetOrderMaterialSnapshotsByOrderIdUseCase;
import com.builderssas.api.domain.port.out.ordermaterialsnapshot.OrderMaterialSnapshotRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener snapshots por id de orden.
 */
@Service
@RequiredArgsConstructor
public class GetOrderMaterialSnapshotsByOrderIdService implements GetOrderMaterialSnapshotsByOrderIdUseCase {

    private final OrderMaterialSnapshotRepositoryPort repository;

    @Override
    public Flux<OrderMaterialSnapshotRecord> getByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }
}
