package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;

import java.time.LocalDate;

/**
 * Mapper Web para transformar entre:
 *  - DTOs de entrada
 *  - Records de dominio
 *  - DTOs de salida
 *
 * No tiene lógica de negocio, solo mapeo estático.
 */
public final class ConstructionRequestWebMapper {

    private ConstructionRequestWebMapper() {}

    // ============================================================
    //  CREATE DTO → DOMAIN RECORD
    // ============================================================
    public static ConstructionRequestRecord toRecord(
            ConstructionRequestCreateDto dto,
            Long userId
    ) {
        return new ConstructionRequestRecord(
                null,                        // id autogenerado
                dto.projectId(),
                dto.constructionTypeId(),
                dto.latitude(),
                dto.longitude(),
                userId,                      // ✔ usuario del header SIEMPRE presente
                LocalDate.now(),             // fecha de solicitud
                RequestStatus.PENDING,       // el orquestador la aprobará o rechazará
                null,                        // observaciones las genera el orquestador
                true                         // activo por defecto
        );
    }

    // ============================================================
    //  DOMAIN RECORD → RESPONSE DTO
    // ============================================================
    public static ConstructionRequestResponseDto toResponse(
            ConstructionRequestRecord record
    ) {
        return new ConstructionRequestResponseDto(
                record.id(),
                record.projectId(),
                record.constructionTypeId(),
                record.latitude(),
                record.longitude(),
                record.requestedByUserId(),
                record.requestDate(),
                record.requestStatus(),
                record.observations(),
                record.active()
        );
    }
}
