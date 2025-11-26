package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementsByOrderUseCase;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para consultar los movimientos de una orden.
 */
@Service
@RequiredArgsConstructor
public class GetInventoryMovementsByOrderService implements GetInventoryMovementsByOrderUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Flux<InventoryMovementRecord> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }
}
