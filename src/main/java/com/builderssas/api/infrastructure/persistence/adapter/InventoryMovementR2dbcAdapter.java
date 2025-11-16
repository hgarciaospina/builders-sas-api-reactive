package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventory.InventoryMovement;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.InventoryMovementMapper;
import com.builderssas.api.infrastructure.persistence.repository.InventoryMovementRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC que implementa el puerto del dominio para
 * la gestión de movimientos de inventario.
 */
@Component
@RequiredArgsConstructor
public class InventoryMovementR2dbcAdapter implements InventoryMovementRepositoryPort {

    private final InventoryMovementRepository repository;

    @Override
    public Mono<InventoryMovement> save(InventoryMovement movement) {
        return repository.save(InventoryMovementMapper.toEntity(movement))
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByMaterialId(Long materialId) {
        return repository.findByMaterialId(materialId)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovement> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(InventoryMovementMapper::toDomain);
    }
}
