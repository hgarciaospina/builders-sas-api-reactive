package com.builderssas.api.application.usecase.ordermaterialsnapshot;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.domain.port.in.ordermaterialsnapshot.GetAllOrderMaterialSnapshotsUseCase;
import com.builderssas.api.domain.port.out.ordermaterialsnapshot.OrderMaterialSnapshotRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener todos los snapshots.
 */
@Service
@RequiredArgsConstructor
public class GetAllOrderMaterialSnapshotsService implements GetAllOrderMaterialSnapshotsUseCase {

    private final OrderMaterialSnapshotRepositoryPort repository;

    @Override
    public Flux<OrderMaterialSnapshotRecord> getAll() {
        return repository.findAll();
    }
}
