package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.inventory.OrderMaterialSnapshot;
import com.builderssas.api.infrastructure.persistence.entity.OrderMaterialSnapshotEntity;

/**
 * Mapper para convertir entre:
 * - OrderMaterialSnapshot (modelo de dominio)
 * - OrderMaterialSnapshotEntity (persistencia)
 */
public final class OrderMaterialSnapshotMapper {

    private OrderMaterialSnapshotMapper() {}

    public static OrderMaterialSnapshotEntity toEntity(Long orderId, OrderMaterialSnapshot domain) {
        var entity = new OrderMaterialSnapshotEntity();
        entity.setOrderId(orderId);
        entity.setMaterialId(domain.materialId());
        entity.setMaterialName(domain.materialName());
        entity.setUnitOfMeasure(domain.unitOfMeasure());
        entity.setStockBefore(domain.stockBefore());
        entity.setRequiredQuantity(domain.requiredQuantity());
        entity.setStockAfter(domain.stockAfter());
        entity.setSnapshotDate(domain.snapshotDate());
        return entity;
    }

    public static OrderMaterialSnapshot toDomain(OrderMaterialSnapshotEntity e) {
        return new OrderMaterialSnapshot(
                e.getMaterialId(),
                e.getMaterialName(),
                e.getUnitOfMeasure(),
                e.getStockBefore(),
                e.getRequiredQuantity(),
                e.getStockAfter(),
                e.getSnapshotDate()
        );
    }
}
