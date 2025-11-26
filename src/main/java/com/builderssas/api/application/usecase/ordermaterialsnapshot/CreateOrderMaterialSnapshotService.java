package com.builderssas.api.application.usecase.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.domain.port.in.ordermaterialsnapshot.CreateOrderMaterialSnapshotUseCase;
import com.builderssas.api.domain.port.out.ordermaterialsnapshot.OrderMaterialSnapshotRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso interno para crear snapshots de manera automática.
 */
@Service
@RequiredArgsConstructor
public class CreateOrderMaterialSnapshotService implements CreateOrderMaterialSnapshotUseCase {

    private final OrderMaterialSnapshotRepositoryPort repository;

    @Override
    public Mono<OrderMaterialSnapshotRecord> create(OrderMaterialSnapshotRecord snapshot) {
        return repository.save(snapshot);
    }
}
