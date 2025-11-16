package com.builderssas.api.infrastructure.web.dto.constructionrequest;

import com.builderssas.api.domain.model.enums.RequestStatus;
import java.time.LocalDate;

/**
 * DTO utilizado para exponer información de una solicitud de construcción.
 *
 * Mantiene una correspondencia exacta 1:1 con el record ConstructionRequestRecord,
 * tanto en nombres como en orden de los atributos. Esto garantiza consistencia
 * entre capas, facilita el mapeo y elimina errores derivados de discrepancias.
 */
public record ConstructionRequestResponseDto(

        Long id,
        Long projectId,
        Long constructionTypeId,
        Double latitude,
        Double longitude,
        Long requestedByUserId,
        LocalDate requestDate,
        RequestStatus requestStatus,
        String observations,
        boolean active

) {}
