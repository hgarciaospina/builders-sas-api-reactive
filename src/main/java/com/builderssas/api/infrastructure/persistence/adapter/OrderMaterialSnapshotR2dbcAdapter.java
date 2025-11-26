package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.domain.port.out.ordermaterialsnapshot.OrderMaterialSnapshotRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.OrderMaterialSnapshotEntity;
import com.builderssas.api.infrastructure.persistence.mapper.OrderMaterialSnapshotMapper;
import com.builderssas.api.infrastructure.persistence.repository.OrderMaterialSnapshotR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC que implementa el puerto de salida para la gestión
 * de snapshots de materiales.
 *
 * Encapsula el acceso a datos y realiza la conversión necesario entre
 * entidades y modelos de dominio.
 */
@Component
@RequiredArgsConstructor
public class OrderMaterialSnapshotR2dbcAdapter implements OrderMaterialSnapshotRepositoryPort {

    private final OrderMaterialSnapshotR2dbcRepository repository;

    @Override
    public Mono<OrderMaterialSnapshotRecord> save(OrderMaterialSnapshotRecord snapshot) {
        OrderMaterialSnapshotEntity entity = OrderMaterialSnapshotMapper.toEntity(snapshot);

        return repository.save(entity)
                .map(OrderMaterialSnapshotMapper::toDomain);
    }

    @Override
    public Flux<OrderMaterialSnapshotRecord> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(OrderMaterialSnapshotMapper::toDomain);
    }

    @Override
    public Flux<OrderMaterialSnapshotRecord> findAll() {
        return repository.findAll()
                .map(OrderMaterialSnapshotMapper::toDomain);
    }
}
