package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.inventory.InventoryMovementRecord;

import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementRequestDto;
import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementResponseDto;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de transformar entre:
 *  - DTOs recibidos desde la capa web
 *  - Records del dominio (InventoryMovementRecord)
 *  - DTOs enviados como respuesta
 *
 * No contiene lógica de negocio.
 * Solo conversión de estructuras.
 */
@Component
public class InventoryMovementWebMapper {

    /**
     * Convierte un DTO de solicitud en un record del dominio.
     *
     * @param dto objeto enviado desde frontend
     * @return record de dominio listo para la capa aplicación
     */
    public InventoryMovementRecord toDomain(InventoryMovementRequestDto dto) {
        return new InventoryMovementRecord(
                null,                           // id siempre null en creación
                dto.materialId(),
                dto.materialName(),
                dto.unitOfMeasure(),
                dto.movementType(),
                dto.quantity(),
                dto.previousStock(),
                dto.finalStock(),
                dto.movementDate(),
                dto.orderId(),
                dto.reason(),
                dto.status(),
                dto.userId(),
                dto.userFullName(),
                dto.userRole()
        );
    }

    /**
     * Convierte un record del dominio en un DTO de respuesta.
     *
     * @param record record proveniente de la capa dominio
     * @return DTO de respuesta para exposición web
     */
    public InventoryMovementResponseDto toResponse(InventoryMovementRecord record) {
        return new InventoryMovementResponseDto(
                record.id(),
                record.materialId(),
                record.materialName(),
                record.unitOfMeasure(),
                record.movementType(),
                record.quantity(),
                record.previousStock(),
                record.finalStock(),
                record.movementDate(),
                record.orderId(),
                record.reason(),
                record.status(),
                record.userId(),
                record.userFullName(),
                record.userRole()
        );
    }
}
