package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventory.OrderMaterialSnapshot;
import com.builderssas.api.domain.port.out.ordermaterialsnapshot.OrderMaterialSnapshotRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.OrderMaterialSnapshotMapper;
import com.builderssas.api.infrastructure.persistence.repository.OrderMaterialSnapshotRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para la persistencia de snapshots de materiales
 * asociados a órdenes.
 */
@Component
@RequiredArgsConstructor
public class OrderMaterialSnapshotR2dbcAdapter implements OrderMaterialSnapshotRepositoryPort {

    private final OrderMaterialSnapshotRepository repository;

    @Override
    public Mono<OrderMaterialSnapshot> save(OrderMaterialSnapshot snapshot) {
        return repository
                .save(OrderMaterialSnapshotMapper.toEntity(null, snapshot))
                .map(OrderMaterialSnapshotMapper::toDomain);
    }

    @Override
    public Flux<OrderMaterialSnapshot> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(OrderMaterialSnapshotMapper::toDomain);
    }
}
