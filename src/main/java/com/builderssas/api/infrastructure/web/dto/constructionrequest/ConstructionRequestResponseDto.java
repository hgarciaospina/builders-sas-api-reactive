package com.builderssas.api.infrastructure.web.dto.constructionrequest;

import com.builderssas.api.domain.model.enums.RequestStatus;
import java.time.LocalDate;

/**
 * DTO utilizado para exponer la información de una ConstructionRequest hacia
 * la capa web.
 *
 * Este objeto refleja el estado final procesado dentro de la aplicación, sin
 * incorporar lógica adicional. Únicamente transporta los datos provenientes del
 * modelo del dominio hacia consumidores externos.
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
        Boolean active

) {}
