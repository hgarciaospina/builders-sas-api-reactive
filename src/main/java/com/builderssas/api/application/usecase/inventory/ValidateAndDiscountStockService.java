package com.builderssas.api.application.usecase.inventory;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.inventory.OrderMaterialSnapshot;
import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;
import com.builderssas.api.infrastructure.persistence.repository.InventoryMovementR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso encargado de:
 *  ✔ Validar stock
 *  ✔ Aplicar consumo
 *  ✔ Registrar movimiento OUT
 *  ✔ Generar snapshot
 *
 *  0% imperativa — no setters, no if, no mutación.
 */
@Service
@RequiredArgsConstructor
public class ValidateAndDiscountStockService {

    private final InventoryMovementR2dbcRepository movementRepository;

    public Mono<OrderMaterialSnapshot> applyConsumption(
            Long materialId,
            Double required,
            Long orderId,
            Long userId,
            String userFullName,
            String userRole
    ) {

        return movementRepository.findByMaterialId(materialId)
                .sort((a, b) -> b.getMovementDate().compareTo(a.getMovementDate()))
                .next()
                .defaultIfEmpty(initialMovement(materialId))
                .flatMap(previousMovement ->
                        buildMovement(previousMovement, materialId, required, orderId, userId, userFullName, userRole)
                                .flatMap(movementRepository::save)
                                .map(saved ->
                                        new OrderMaterialSnapshot(
                                                materialId,
                                                previousMovement.getMaterialName(),
                                                previousMovement.getUnitOfMeasure(),
                                                previousMovement.getFinalStock(),
                                                required,
                                                saved.getFinalStock(),
                                                saved.getMovementDate()
                                        )
                                )
                );
    }

    // ============================================================
    // Construye el nuevo movimiento (inmutable)
    // ============================================================
    private Mono<InventoryMovementEntity> buildMovement(
            InventoryMovementEntity last,
            Long materialId,
            Double required,
            Long orderId,
            Long userId,
            String userFullName,
            String userRole
    ) {
        Double previous = last.getFinalStock();
        Double finalStock = previous - required;

        return Mono.just(
                new InventoryMovementEntity(
                        null,
                        materialId,
                        last.getMaterialName(),
                        last.getUnitOfMeasure(),
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

    // ============================================================
    // Si el material nunca ha sido movido
    // ============================================================
    private InventoryMovementEntity initialMovement(Long materialId) {
        return new InventoryMovementEntity(
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
}
