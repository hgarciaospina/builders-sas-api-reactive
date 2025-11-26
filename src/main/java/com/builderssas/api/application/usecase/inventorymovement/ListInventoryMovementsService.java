package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventorymovement.ListInventoryMovementsUseCase;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para listar todos los movimientos
 * de inventario registrados.
 *
 * Este servicio delega la consulta al puerto de salida sin agregar
 * lógica adicional.
 */
@Service
@RequiredArgsConstructor
public class ListInventoryMovementsService implements ListInventoryMovementsUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Flux<InventoryMovementRecord> listAll() {
        return repository.findAll();
    }
}
