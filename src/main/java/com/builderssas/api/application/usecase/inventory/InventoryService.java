package com.builderssas.api.application.usecase.inventory;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.domain.port.in.inventory.GetInventoryUseCase;
import com.builderssas.api.domain.port.in.inventory.IncreaseInventoryUseCase;
import com.builderssas.api.domain.port.in.inventory.DecreaseInventoryUseCase;
import com.builderssas.api.domain.port.in.inventory.ValidateEnoughStockUseCase;
import com.builderssas.api.domain.port.out.inventory.InventoryRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.InvalidBusinessRuleException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación encargado de manejar el stock VIVO del inventario.
 *
 * Este servicio:
 *  • Opera exclusivamente sobre stock actual.
 *  • No maneja movimientos (eso es tarea de MovementService).
 *  • Totalmente reactivo, funcional e inmutable.
 */
@Service
@RequiredArgsConstructor
public class InventoryService implements
        GetInventoryUseCase,
        IncreaseInventoryUseCase,
        DecreaseInventoryUseCase,
        ValidateEnoughStockUseCase {

    private final InventoryRepositoryPort inventoryRepo;

    @Override
    public Mono<InventoryRecord> findByMaterialId(Long materialId) {
        return inventoryRepo.findByMaterialId(materialId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Inventario no encontrado para el material.")
                ));
    }

    @Override
    public Mono<Void> increase(Long materialId, Double quantity) {

        Mono<InventoryRecord> current =
                inventoryRepo.findByMaterialId(materialId)
                        .defaultIfEmpty(new InventoryRecord(
                                null,
                                materialId,
                                0.0,
                                LocalDateTime.now()
                        ));

        return current
                .map(rec -> new InventoryRecord(
                        rec.id(),
                        rec.materialId(),
                        rec.stock() + quantity,
                        LocalDateTime.now()
                ))
                .flatMap(inventoryRepo::save)
                .then();
    }

    @Override
    public Mono<Void> decrease(Long materialId, Double quantity) {

        return inventoryRepo.findByMaterialId(materialId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Inventario no encontrado para disminuir stock.")
                ))
                .flatMap(rec -> {

                    Double after = rec.stock() - quantity;

                    return Mono.just(after)
                            .filter(a -> a >= 0)
                            .switchIfEmpty(Mono.error(
                                    new InvalidBusinessRuleException("Stock insuficiente para realizar la operación.")
                            ))
                            .map(a -> new InventoryRecord(
                                    rec.id(),
                                    rec.materialId(),
                                    a,
                                    LocalDateTime.now()
                            ));
                })
                .flatMap(inventoryRepo::save)
                .then();
    }

    @Override
    public Mono<Boolean> hasEnough(Long materialId, Double required) {

        return inventoryRepo.findByMaterialId(materialId)
                .map(InventoryRecord::stock)
                .map(stock -> stock >= required)
                .defaultIfEmpty(false);
    }
}
