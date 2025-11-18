package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import com.builderssas.api.infrastructure.web.dto.constructionorder.CreateConstructionOrderRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper Web encargado de convertir:
 *
 *   • ConstructionOrderRecord → ConstructionOrderResponseDto
 *   • CreateConstructionOrderRequestDto → ConstructionOrderRecord
 *
 * Su función es estrictamente de transformación entre capas,
 * sin incluir ninguna lógica de negocio ni validaciones.
 *
 * Todas las transformaciones mantienen el principio de inmutabilidad
 * y siguen el flujo funcional del sistema.
 */
@Component
public class ConstructionOrderWebMapper {

    /**
     * Convierte un record de dominio en un DTO de respuesta.
     *
     * @param r instancia inmutable de ConstructionOrderRecord
     * @return DTO listo para ser enviado al cliente
     */
    public ConstructionOrderResponseDto toResponse(ConstructionOrderRecord r) {
        return new ConstructionOrderResponseDto(
                r.id(),
                r.constructionRequestId(),
                r.projectId(),
                r.constructionTypeId(),
                r.requestedByUserId(),
                r.latitude(),
                r.longitude(),
                r.requestedDate(),
                r.scheduledStartDate(),
                r.scheduledEndDate(),
                r.orderStatus(),
                r.createdAt(),
                r.updatedAt(),
                r.observations(),
                r.active()
        );
    }

    /**
     * Convierte el DTO de creación proveniente de la capa web
     * en el record de dominio ConstructionOrderRecord.
     *
     * Campos de control como id, createdAt y updatedAt se
     * inicializan en null para que la capa de aplicación
     * (use case) sea la responsable de asignarlos.
     *
     * @param dto DTO recibido en el endpoint de creación
     * @return record de dominio listo para ser procesado por el caso de uso
     */
    public ConstructionOrderRecord toDomain(CreateConstructionOrderRequestDto dto) {
        return new ConstructionOrderRecord(
                null,                           // id -> lo genera la BD
                dto.constructionRequestId(),
                dto.projectId(),
                dto.constructionTypeId(),
                dto.requestedByUserId(),
                dto.latitude(),
                dto.longitude(),
                dto.requestedDate(),
                dto.scheduledStartDate(),
                dto.scheduledEndDate(),
                dto.orderStatus(),              // IN_PROGRESS viene desde el body por ahora
                null,                           // createdAt -> lo asigna el use case
                null,                           // updatedAt -> lo asigna el use case
                dto.observations(),
                dto.active()
        );
    }
}
