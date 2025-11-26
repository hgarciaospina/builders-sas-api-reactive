package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.infrastructure.web.dto.ordermaterialsnapshot.CreateOrderMaterialSnapshotDto;
import com.builderssas.api.infrastructure.web.dto.ordermaterialsnapshot.OrderMaterialSnapshotDto;

import java.time.LocalDateTime;

/**
 * Mapper para convertir entre DTOs web y modelos de dominio
 * para los snapshots de materiales.
 *
 * Este mapper NO realiza ninguna mutación y la conversión es 1:1.
 */
public final class OrderMaterialSnapshotWebMapper {

    private OrderMaterialSnapshotWebMapper() {
        // Utility class
    }

    /**
     * Convierte un DTO de creación en un modelo de dominio.
     * Este DTO se usa internamente cuando el sistema genera un snapshot.
     *
     * @param dto datos provenientes del servicio de órdenes.
     * @return dominio listo para persistencia.
     */
    public static OrderMaterialSnapshotRecord toDomain(CreateOrderMaterialSnapshotDto dto) {
        return new OrderMaterialSnapshotRecord(
                null,                       // id generado por la BD
                dto.orderId(),
                dto.materialId(),
                dto.materialName(),
                dto.unitOfMeasure(),
                dto.stockBefore(),
                dto.requiredQuantity(),
                dto.stockAfter(),
                LocalDateTime.now(),        // snapshotDate generado automáticamente
                LocalDateTime.now(),        // createdAt
                LocalDateTime.now()         // updatedAt (igual a createdAt)
        );
    }

    /**
     * Convierte un modelo de dominio en un DTO de salida para la API.
     *
     * @param domain snapshot del dominio.
     * @return DTO expuesto al cliente.
     */
    public static OrderMaterialSnapshotDto toDto(OrderMaterialSnapshotRecord domain) {
        return new OrderMaterialSnapshotDto(
                domain.id(),
                domain.orderId(),
                domain.materialId(),
                domain.materialName(),
                domain.unitOfMeasure(),
                domain.stockBefore(),
                domain.requiredQuantity(),
                domain.stockAfter(),
                domain.snapshotDate(),
                domain.createdAt(),
                domain.updatedAt()
        );
    }
}
