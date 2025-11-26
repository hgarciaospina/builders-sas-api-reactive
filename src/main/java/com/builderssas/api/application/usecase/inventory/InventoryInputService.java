package com.builderssas.api.application.usecase.inventory;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.port.in.inventory.RegisterInventoryInputUseCase;
import com.builderssas.api.domain.port.out.inventory.InventoryRepositoryPort;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Application service responsible for handling inventory INPUT operations.
 *
 * Responsibilities:
 *  • Update the live inventory stock (Inventory table).
 *  • Create historical movement records (InventoryMovement).
 *  • Validate user and material existence.
 *  • Fully reactive, functional and immutable.
 *
 * Architectural Notes:
 *  • Implements RegisterInventoryInputUseCase (domain entry point).
 *  • Does not contain business rules for OUTPUT movements.
 *  • Delegates stock handling to InventoryService.
 */
@Service
@RequiredArgsConstructor
public class InventoryInputService implements RegisterInventoryInputUseCase {

    private final InventoryService inventoryService;
    private final InventoryRepositoryPort inventoryRepo;
    private final InventoryMovementRepositoryPort movementRepo;
    private final MaterialTypeRepositoryPort materialRepo;
    private final UserRepositoryPort userRepo;

    // ============================================================
    //      REGISTER INPUT (IMPLEMENTATION OF USE CASE)
    // ============================================================
    @Override
    public Mono<Void> registerInput(Long materialId, Double quantity, InventoryReason reason, Long userId) {

        Mono<InventoryRecord> currentStock =
                inventoryRepo.findByMaterialId(materialId)
                        .defaultIfEmpty(new InventoryRecord(null, materialId, 0.0, LocalDateTime.now()));

        Mono<String> materialName =
                materialRepo.findById(materialId)
                        .map(mat -> mat.name())
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Material no existe")));

        Mono<String> unit =
                materialRepo.findById(materialId)
                        .map(mat -> mat.unitOfMeasure());

        Mono<String> userName =
                userRepo.findById(userId)
                        .map(u -> u.displayName())
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario no existe")));

        return Mono.zip(currentStock, materialName, unit, userName)
                .flatMap(tuple -> {
                    InventoryRecord stock = tuple.getT1();
                    String matName = tuple.getT2();
                    String unitOfMeasure = tuple.getT3();
                    String userDisplay = tuple.getT4();

                    Double previous = stock.stock();
                    Double newStock = previous + quantity;

                    InventoryMovementRecord movement =
                            new InventoryMovementRecord(
                                    null,
                                    materialId,
                                    matName,
                                    unitOfMeasure,
                                    InventoryMovementType.IN,
                                    quantity,
                                    previous,
                                    newStock,
                                    LocalDateTime.now(),
                                    null,
                                    reason,
                                    InventoryStatus.COMPLETED,
                                    userId,
                                    userDisplay,
                                    null // role optional, not needed for input
                            );

                    return movementRepo.save(movement)
                            .then(inventoryService.increase(materialId, quantity));
                });
    }
}
