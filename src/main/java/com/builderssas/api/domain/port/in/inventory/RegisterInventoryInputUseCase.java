package com.builderssas.api.domain.port.in.inventory;

import com.builderssas.api.domain.model.enums.InventoryReason;
import reactor.core.publisher.Mono;

/**
 * Use case: register an inventory INPUT movement.
 *
 * Responsibilities:
 *  • Define the public contract for inventory input operations.
 *  • Serves as entry point for Web controllers.
 *  • Decouples application logic from the transport layer.
 *
 * Notes:
 *  • Implemented in the application layer (InventoryInputService).
 *  • Fully reactive (Mono<Void>).
 */
public interface RegisterInventoryInputUseCase {

    /**
     * Registers an inventory input operation.
     *
     * @param materialId material identifier
     * @param quantity   positive quantity to add
     * @param reason     business reason for the input
     * @param userId     user executing the input
     * @return Mono<Void> completion signal
     */
    Mono<Void> registerInput(Long materialId, Double quantity, InventoryReason reason, Long userId);
}
