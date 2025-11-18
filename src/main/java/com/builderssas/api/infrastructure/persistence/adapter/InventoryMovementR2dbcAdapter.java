package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventory.InventoryMovement;

import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.InventoryMovementMapper;
import com.builderssas.api.infrastructure.persistence.repository.InventoryMovementR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para movimientos de inventario.
 * 100% funcional, sin setters, sin imperativa.
 */
@Component
@RequiredArgsConstructor
public class InventoryMovementR2dbcAdapter implements InventoryMovementRepositoryPort {

    private final InventoryMovementR2dbcRepository repository;
    private final InventoryMovementMapper mapper;

    @Override
    public Mono<InventoryMovement> save(InventoryMovement movement) {
        return repository
                .save(mapper.toEntity(movement))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByMaterialId(Long materialId) {
        return repository
                .findByMaterialId(materialId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByOrderId(Long orderId) {
        return repository
                .findByOrderId(orderId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByUserId(Long userId) {
        return repository
                .findByUserId(userId)
                .map(mapper::toDomain);
    }

    public Flux<InventoryMovement> findByMovementDateRange(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    ) {
        return repository
                .findByMovementDateBetween(start, end)
                .map(mapper::toDomain);
    }
}
