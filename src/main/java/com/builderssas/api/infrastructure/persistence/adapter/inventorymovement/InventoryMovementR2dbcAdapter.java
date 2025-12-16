package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.InventoryMovementMapper;
import com.builderssas.api.infrastructure.persistence.repository.InventoryMovementR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC que implementa el puerto InventoryMovementRepositoryPort.
 * Se encarga de convertir entre entidades persistentes y modelos de dominio.
 */
@Component
@RequiredArgsConstructor
public class InventoryMovementR2dbcAdapter implements InventoryMovementRepositoryPort {

    private final InventoryMovementR2dbcRepository repository;

    @Override
    public Mono<InventoryMovementRecord> save(InventoryMovementRecord movement) {
        return Mono.just(movement)
                .map(InventoryMovementMapper::toEntity)
                .flatMap(repository::save)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovementRecord> findAll() {
        return repository.findAll()
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Mono<InventoryMovementRecord> findById(Long id) {
        return repository.findById(id)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovementRecord> findByMaterialId(Long materialId) {
        return repository.findByMaterialId(materialId)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovementRecord> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(InventoryMovementMapper::toDomain);
    }

    @Override
    public Flux<InventoryMovementRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Nuevo método: obtiene el último movimiento registrado para un material.
     * Este método evita sorting manual y permite obtener el stock actual.
     */
    @Override
    public Mono<InventoryMovementRecord> findLastByMaterialId(Long materialId) {
        return repository.findLastByMaterialId(materialId)
                .map(InventoryMovementMapper::toDomain);
    }
}
