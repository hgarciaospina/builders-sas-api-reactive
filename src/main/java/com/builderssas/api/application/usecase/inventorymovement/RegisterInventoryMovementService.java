package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.RegisterInventoryMovementUseCase;

import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para registrar un movimiento
 * de inventario ya calculado.
 *
 * Esta clase no calcula stocks; simplemente persiste el movimiento
 * recibido según el contrato del dominio.
 */
@Service
@RequiredArgsConstructor
public class RegisterInventoryMovementService implements RegisterInventoryMovementUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Mono<InventoryMovementRecord> registerMovement(InventoryMovementRecord movement) {
        return repository.save(movement);
    }
}
