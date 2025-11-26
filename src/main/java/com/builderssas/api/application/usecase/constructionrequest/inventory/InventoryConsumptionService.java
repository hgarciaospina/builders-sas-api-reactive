package com.builderssas.api.application.usecase.constructionrequest.inventory;

import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService;
import com.builderssas.api.application.usecase.inventory.InventoryService;
import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.model.user.UserRecord;

import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Application service responsible for handling the actual consumption of
 * inventory stock during the creation of a construction order.
 *
 * Responsibilities:
 *  • Decrease live inventory for each required material.
 *  • Register a historical movement for traceability.
 *  • Resolve user and role information for audit purposes.
 *
 * Concurrency Note:
 *  concatMap(...) is used instead of flatMap(...) to guarantee sequential
 *  execution of stock operations, preventing race conditions that would
 *  corrupt the live inventory state when multiple items refer to the same
 *  material. This preserves full reactivity without blocking.
 */
@Service
@RequiredArgsConstructor
public class InventoryConsumptionService {

    private final InventoryMovementRepositoryPort movementRepo;
    private final MaterialTypeRepositoryPort materialRepo;
    private final UserRepositoryPort userRepo;
    private final UserRoleRepositoryPort userRoleRepo;
    private final RoleRepositoryPort roleRepo;

    // Injected by Spring — no manual constructor modification required.
    private final InventoryService inventoryService;

    private record UserContext(UserRecord user, RoleRecord role) {}

    /**
     * Performs the inventory consumption process and registers historical
     * movements associated with a construction order.
     *
     * @param request the source construction request
     * @param orderId the generated order identifier
     * @param stock   validated stock summary for all required materials
     * @return Mono<Void> upon completion
     */
    public Mono<Void> consume(
            ConstructionRequestRecord request,
            Long orderId,
            StockValidationService.StockSummary stock
    ) {

        Mono<UserRecord> mUser =
                userRepo.findById(request.requestedByUserId());

        Mono<RoleRecord> mRole =
                userRoleRepo.findByUserId(request.requestedByUserId())
                        .next()
                        .flatMap(ur -> roleRepo.findById(ur.roleId()));

        Mono<UserContext> userCtx = mUser.zipWith(mRole, UserContext::new);

        return userCtx.flatMapMany(ctx ->
                Flux.fromIterable(stock.items())

                        // ==================================================
                        // 1. SAFE INVENTORY DECREMENT (SEQUENTIAL - REACTIVE)
                        // ==================================================
                        .concatMap(item ->
                                inventoryService
                                        .decrease(item.materialTypeId(), item.required())

                                        // ==================================================
                                        // 2. SAVE HISTORICAL MOVEMENT ENTRY
                                        // ==================================================
                                        .then(
                                                materialRepo.findById(item.materialTypeId())
                                                        .map(mat -> buildMovement(item, mat, orderId, ctx))
                                                        .flatMap(movementRepo::save)
                                        )
                        )
        ).then();
    }

    /**
     * Builds the historical movement record for audit and traceability.
     */
    private InventoryMovementRecord buildMovement(
            StockValidationService.MaterialCheck item,
            MaterialTypeRecord mat,
            Long orderId,
            UserContext ctx
    ) {

        Double previous = item.available();
        Double required = item.required();
        Double finalStock = previous - required;

        return new InventoryMovementRecord(
                null,
                item.materialTypeId(),
                item.materialName(),
                mat.unitOfMeasure(),
                InventoryMovementType.OUT,
                required,
                previous,
                finalStock,
                LocalDateTime.now(),
                orderId,
                InventoryReason.ORDER_CONSUMPTION,
                InventoryStatus.COMPLETED,
                ctx.user().id(),
                ctx.user().displayName(),
                ctx.role().name()
        );
    }
}
