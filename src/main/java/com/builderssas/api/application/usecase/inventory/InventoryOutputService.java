package com.builderssas.api.application.usecase.inventory;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.InvalidBusinessRuleException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Application service responsible for handling manual inventory OUTPUT operations.
 *
 * Responsibilities:
 *  • Validate and decrease live stock (Inventory table).
 *  • Ensure stock is available before decreasing.
 *  • Persist an immutable InventoryMovementRecord of type OUT.
 *  • Attach user and role information for full audit traceability.
 *
 * Characteristics:
 *  • Fully reactive and immutable.
 *  • Pure application layer (no Web, no SQL).
 *  • No imperative branching (no if/else, no ternaries).
 */
@Service
@RequiredArgsConstructor
public class InventoryOutputService {

    private final InventoryService inventoryService;
    private final InventoryMovementRepositoryPort movementRepo;
    private final MaterialTypeRepositoryPort materialRepo;
    private final UserRepositoryPort userRepo;
    private final UserRoleRepositoryPort userRoleRepo;
    private final RoleRepositoryPort roleRepo;

    private record UserContext(UserRecord user, RoleRecord role) {}

    /**
     * Registers a manual OUT operation (damage, loss, return, adjustment, etc.).
     *
     * Steps:
     *  1. Validate quantity > 0.
     *  2. Validate enough stock (no negative possible).
     *  3. Decrease stock in live inventory.
     *  4. Reload updated stock snapshot.
     *  5. Load material data.
     *  6. Load user + role.
     *  7. Persist movement (OUT).
     *
     * @param materialId material identifier
     * @param quantity   positive quantity to remove
     * @param reason     business reason (domain enum)
     * @param userId     user performing the operation
     * @return Mono<Void>
     */
    public Mono<Void> registerOutput(
            Long materialId,
            Double quantity,
            InventoryReason reason,
            Long userId
    ) {

        Mono<Double> validQuantity =
                Mono.justOrEmpty(quantity)
                        .filter(q -> q != null && q > 0)
                        .switchIfEmpty(Mono.error(
                                new InvalidBusinessRuleException("La cantidad debe ser mayor que cero.")
                        ));

        Mono<Boolean> hasStock =
                inventoryService.hasEnough(materialId, quantity)
                        .filter(Boolean::booleanValue)
                        .switchIfEmpty(Mono.error(
                                new InvalidBusinessRuleException("Stock insuficiente para realizar esta salida.")
                        ));

        Mono<UserContext> userContext =
                userRepo.findById(userId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("El usuario no existe.")))
                        .zipWith(
                                userRoleRepo.findByUserId(userId)
                                        .next()
                                        .flatMap(ur -> roleRepo.findById(ur.roleId()))
                                        .switchIfEmpty(Mono.error(
                                                new ResourceNotFoundException("El rol del usuario no existe.")
                                        )),
                                UserContext::new
                        );

        Mono<MaterialTypeRecord> materialMono =
                materialRepo.findById(materialId)
                        .switchIfEmpty(Mono.error(
                                new ResourceNotFoundException("El material no existe.")
                        ));

        return validQuantity
                .then(hasStock)
                .then(inventoryService.decrease(materialId, quantity))
                .then(
                        inventoryService.findByMaterialId(materialId)
                                .zipWith(materialMono)
                                .zipWith(userContext)
                                .map(tuple -> {

                                    var inv = tuple.getT1().getT1();
                                    var material = tuple.getT1().getT2();
                                    var ctx = tuple.getT2();

                                    Double finalStock = inv.stock();
                                    Double previousStock = finalStock + quantity;

                                    return new InventoryMovementRecord(
                                            null,
                                            materialId,
                                            material.name(),
                                            material.unitOfMeasure(),
                                            InventoryMovementType.OUT,
                                            quantity,
                                            previousStock,
                                            finalStock,
                                            LocalDateTime.now(),
                                            null,
                                            reason,
                                            InventoryStatus.COMPLETED,
                                            ctx.user().id(),
                                            ctx.user().displayName(),
                                            ctx.role().name()
                                    );
                                })
                                .flatMap(movementRepo::save)
                )
                .then();
    }
}
