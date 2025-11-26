package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.infrastructure.web.dto.inventory.InventoryDto;

/**
 * Mapper Web para conversión:
 *  • InventoryRecord → InventoryDto (respuesta pública)
 *
 * Notas:
 *  • No existe conversión de entrada.
 *  • Ningún usuario puede modificar Inventario directamente.
 *  • Toda modificación se hace mediante Input/Output Services.
 */
public final class InventoryWebMapper {

    private InventoryWebMapper() {}

    public static InventoryDto toResponse(InventoryRecord record) {
        return new InventoryDto(
                record.id(),
                record.materialId(),
                record.stock(),
                record.updatedAt()
        );
    }
}
