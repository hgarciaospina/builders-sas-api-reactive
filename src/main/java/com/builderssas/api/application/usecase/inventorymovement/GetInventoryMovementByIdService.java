package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementByIdUseCase;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso que permite obtener un movimiento
 * de inventario por su ID.
 */
@Service
@RequiredArgsConstructor
public class GetInventoryMovementByIdService implements GetInventoryMovementByIdUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Mono<InventoryMovementRecord> findById(Long id) {
        return repository.findById(id);
    }
}
