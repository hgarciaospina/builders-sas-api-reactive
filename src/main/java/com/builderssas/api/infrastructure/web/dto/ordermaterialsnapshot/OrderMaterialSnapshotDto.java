package com.builderssas.api.infrastructure.web.dto.ordermaterialsnapshot;

import java.time.LocalDateTime;

/**
 * DTO de salida que representa un snapshot completo.
 */
public record OrderMaterialSnapshotDto(
        Long id,
        Long orderId,
        Long materialId,
        String materialName,
        String unitOfMeasure,
        Double stockBefore,
        Double requiredQuantity,
        Double stockAfter,
        LocalDateTime snapshotDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
