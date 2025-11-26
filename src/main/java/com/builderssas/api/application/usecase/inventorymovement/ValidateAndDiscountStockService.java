package com.builderssas.api.application.usecase.inventorymovement;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.model.inventorymovement.OrderMaterialSnapshot;
import com.builderssas.api.domain.port.in.inventorymovement.ValidateAndDiscountStockUseCase;

import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio que implementa el caso de uso ValidateAndDiscountStockUseCase.
 */
@Service
@RequiredArgsConstructor
public class ValidateAndDiscountStockService implements ValidateAndDiscountStockUseCase {

    private final InventoryMovementRepositoryPort repository;

    @Override
    public Mono<OrderMaterialSnapshot> applyConsumption(
            Long materialId,
            Double required,
            Long orderId,
            Long userId,
            String userFullName,
            String userRole
    ) {

        return repository.findByMaterialId(materialId)
                .sort((a, b) -> b.movementDate().compareTo(a.movementDate()))
                .next()
                .defaultIfEmpty(initialMovement(materialId))
                .flatMap(previousMovement ->
                        buildMovement(previousMovement, materialId, required, orderId, userId, userFullName, userRole)
                                .flatMap(repository::save)
                                .map(saved ->
                                        new OrderMaterialSnapshot(
                                                materialId,
                                                previousMovement.materialName(),
                                                previousMovement.unitOfMeasure(),
                                                previousMovement.finalStock(),
                                                required,
                                                saved.finalStock(),
                                                saved.movementDate()
                                        )
                                )
                );
    }

    private InventoryMovementRecord initialMovement(Long materialId) {
        return new InventoryMovementRecord(
                null,
                materialId,
                "UNKNOWN",
                "UNIT",
                InventoryMovementType.IN,
                0.0,
                0.0,
                0.0,
                LocalDateTime.MIN,
                null,
                InventoryReason.INITIAL_STOCK,
                InventoryStatus.COMPLETED,
                null,
                null,
                null
        );
    }

    private Mono<InventoryMovementRecord> buildMovement(
            InventoryMovementRecord last,
            Long materialId,
            Double required,
            Long orderId,
            Long userId,
            String userFullName,
            String userRole
    ) {
        Double previous = last.finalStock();
        Double finalStock = previous - required;

        return Mono.just(
                new InventoryMovementRecord(
                        null,
                        materialId,
                        last.materialName(),
                        last.unitOfMeasure(),
                        InventoryMovementType.OUT,
                        required,
                        previous,
                        finalStock,
                        LocalDateTime.now(),
                        orderId,
                        InventoryReason.ORDER_CONSUMPTION,
                        InventoryStatus.COMPLETED,
                        userId,
                        userFullName,
                        userRole
                )
        );
    }
}
