package com.builderssas.api.infrastructure.web.dto.inventory;

import com.builderssas.api.domain.model.enums.InventoryReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for inventory OUTPUT operations.
 * Pure data carrier for Web layer.
 */
public record InventoryOutputDto(

        @NotNull(message = "El material es obligatorio.")
        Long materialId,

        @NotNull(message = "La cantidad es obligatoria.")
        @Positive(message = "La cantidad debe ser mayor que cero.")
        Double quantity,

        @NotNull(message = "El motivo es obligatorio.")
        InventoryReason reason
) {}
