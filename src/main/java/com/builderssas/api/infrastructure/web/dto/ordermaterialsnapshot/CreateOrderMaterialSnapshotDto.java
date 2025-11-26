package com.builderssas.api.infrastructure.web.dto.ordermaterialsnapshot;

/**
 * DTO utilizado INTERNAMENTE por el sistema para crear un snapshot
 * de materiales cuando se genera una orden de construcción.
 *
 * Este DTO NO es expuesto al usuario.
 * No lleva validaciones de entrada.
 * No forma parte de ningún endpoint público.
 */
public record CreateOrderMaterialSnapshotDto(

        Long orderId,
        Long materialId,
        String materialName,
        String unitOfMeasure,
        Double stockBefore,
        Double requiredQuantity,
        Double stockAfter

) {}
