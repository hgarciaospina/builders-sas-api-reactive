package com.builderssas.api.domain.model.constructionrequest;

import com.builderssas.api.domain.model.enums.RequestStatus;

import java.time.LocalDate;

public record ConstructionRequestRecord(
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
