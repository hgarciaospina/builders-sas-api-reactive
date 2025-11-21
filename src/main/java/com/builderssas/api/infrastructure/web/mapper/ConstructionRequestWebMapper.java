package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;

import java.time.LocalDate;

/**
 * Mapper Web estático para convertir entre:
 *
 *   • ConstructionRequestCreateDto → ConstructionRequestRecord
 *   • ConstructionRequestRecord → ConstructionRequestResponseDto
 *
 * No contiene lógica de negocio.
 * No depende de Spring.
 * No se inyecta en controladores.
 *
 * Sigue el modelo utilizado para todos los web mappers del proyecto.
 */
public final class ConstructionRequestWebMapper {

    private ConstructionRequestWebMapper() { }

    /**
     * Convierte el DTO recibido en el POST en un record del dominio.
     *
     * @param dto datos enviados por el cliente
     * @return record inmutable para el caso de uso
     */
    public static ConstructionRequestRecord toRecord(ConstructionRequestCreateDto dto) {
        return new ConstructionRequestRecord(
                null,                   // id → lo genera la BD
                dto.projectId(),
                dto.constructionTypeId(),
                dto.latitude(),
                dto.longitude(),
                null,                   // requestedByUserId → lo asignará seguridad
                LocalDate.now(),        // requestedDate → generado automáticamente
                RequestStatus.PENDING,  // estado inicial
                null,                   // observations
                Boolean.TRUE            // active
        );
    }

    /**
     * Convierte el record del dominio en un DTO de respuesta.
     *
     * @param r instance del record
     * @return DTO para exponer al cliente
     */
    public static ConstructionRequestResponseDto toResponse(ConstructionRequestRecord r) {
        return new ConstructionRequestResponseDto(
                r.id(),
                r.projectId(),
                r.constructionTypeId(),
                r.latitude(),
                r.longitude(),
                r.requestedByUserId(),
                r.requestDate(),
                r.requestStatus(),
                r.observations(),
                r.active()
        );
    }
}
