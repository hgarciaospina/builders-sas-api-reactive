package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.ListInventoryMovementsByUserUseCase;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener
 * los movimientos de inventario registrados por
 * un usuario específico.
 *
 * 0% imperativa — solo delega al repositorio del dominio.
 */
@Service
@RequiredArgsConstructor
public class ListInventoryMovementsByUserService
        implements ListInventoryMovementsByUserUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Flux<InventoryMovementRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
