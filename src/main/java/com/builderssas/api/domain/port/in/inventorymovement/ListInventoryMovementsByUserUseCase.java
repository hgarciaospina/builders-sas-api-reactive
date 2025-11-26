package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso encargado de obtener todos los movimientos
 * de inventario realizados por un usuario específico.
 *
 * Pertenece al dominio y define el contrato que la capa
 * de aplicación debe implementar. No conoce detalles de
 * infraestructura ni persistencia.
 */
public interface ListInventoryMovementsByUserUseCase {

    /**
     * Obtiene todos los movimientos asociados a un usuario.
     *
     * @param userId identificador del usuario
     * @return flujo reactivo con los movimientos del usuario
     */
    Flux<InventoryMovementRecord> findByUserId(Long userId);
}
