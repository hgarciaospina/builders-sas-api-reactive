package com.builderssas.api.infrastructure.web.dto.inventorymovement;

/**
 * DTO para solicitar consumo de inventario asociado a una orden.
 */
public record OrderConsumptionRequestDto(
        Long materialId,
        Double required,
        Long orderId,
        Long userId,
        String userFullName,
        String userRole
) { }
