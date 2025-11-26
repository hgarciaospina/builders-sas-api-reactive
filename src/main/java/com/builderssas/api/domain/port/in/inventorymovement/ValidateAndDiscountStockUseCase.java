package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.OrderMaterialSnapshot;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada del dominio que define la operación para
 * validar stock, aplicar consumo y generar el snapshot
 * correspondiente.
 *
 * Este caso de uso aplica las reglas necesarias para descontar
 * material durante la creación o avance de una orden de construcción.
 */
public interface ValidateAndDiscountStockUseCase {

    Mono<OrderMaterialSnapshot> applyConsumption(
            Long materialId,
            Double required,
            Long orderId,
            Long userId,
            String userFullName,
            String userRole
    );
}
