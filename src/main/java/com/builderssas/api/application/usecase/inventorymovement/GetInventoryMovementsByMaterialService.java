package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementsByMaterialUseCase;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para consultar movimientos por material.
 */
@Service
@RequiredArgsConstructor
public class GetInventoryMovementsByMaterialService implements GetInventoryMovementsByMaterialUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Flux<InventoryMovementRecord> findByMaterialId(Long materialId) {
        return repository.findByMaterialId(materialId);
    }
}