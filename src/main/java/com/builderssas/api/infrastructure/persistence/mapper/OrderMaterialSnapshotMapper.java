package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.ordermaterialsnapshot.OrderMaterialSnapshotRecord;
import com.builderssas.api.infrastructure.persistence.entity.OrderMaterialSnapshotEntity;

/**
 * Mapper responsable de convertir entre la entidad persistente
 * y el modelo de dominio para los snapshots de materiales.
 *
 * La conversión es 1:1 dado que ambos modelos son inmutables
 * y utilizan los mismos tipos de datos.
 */
public final class OrderMaterialSnapshotMapper {

    private OrderMaterialSnapshotMapper() {
        // Utility class
    }

    /**
     * Convierte una entidad persistida en un modelo de dominio.
     *
     * @param entity entidad persistente.
     * @return modelo de dominio.
     */
    public static OrderMaterialSnapshotRecord toDomain(OrderMaterialSnapshotEntity entity) {
        return new OrderMaterialSnapshotRecord(
                entity.getId(),
                entity.getOrderId(),
                entity.getMaterialId(),
                entity.getMaterialName(),
                entity.getUnitOfMeasure(),
                entity.getStockBefore(),
                entity.getRequiredQuantity(),
                entity.getStockAfter(),
                entity.getSnapshotDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Convierte un modelo de dominio a entidad persistente.
     *
     * @param domain modelo de dominio.
     * @return entidad persistente lista para guardarse.
     */
    public static OrderMaterialSnapshotEntity toEntity(OrderMaterialSnapshotRecord domain) {
        return new OrderMaterialSnapshotEntity(
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
