package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.infrastructure.persistence.entity.InventoryEntity;

public final class InventoryMapper {

    private InventoryMapper() {}

    public static InventoryEntity toEntity(InventoryRecord record) {
        return new InventoryEntity(
                record.id(),
                record.materialId(),
                record.stock(),
                record.updatedAt()
        );
    }

    public static InventoryRecord toDomain(InventoryEntity entity) {
        return new InventoryRecord(
                entity.getId(),
                entity.getMaterialId(),
                entity.getStock(),
                entity.getUpdatedAt()
        );
    }
}
