package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada para consultar todos los movimientos de inventario.
 *
 * Este caso de uso permite recuperar el historial completo de
 * InventoryMovement, sin filtros. Es utilizado por la capa de
 * presentación para exponer los datos consolidados.
 *
 * Arquitectura:
 * - Pertenece al dominio.
 * - Define la operación que la aplicación debe ofrecer.
 * - No conoce detalles de infraestructura.
 */
public interface ListInventoryMovementsUseCase {

    /**
     * Obtiene todos los movimientos de inventario registrados.
     *
     * @return flujo reactivo con todos los movimientos
     */
    Flux<InventoryMovementRecord> listAll();
}
