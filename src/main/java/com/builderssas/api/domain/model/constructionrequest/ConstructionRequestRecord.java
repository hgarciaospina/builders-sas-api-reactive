package com.builderssas.api.domain.model.constructionrequest;

import com.builderssas.api.domain.model.enums.RequestStatus;

public record ConstructionRequestRecord(
        Long id,
        Long projectId,
        Long constructionTypeId,
        Double latitude,
        Double longitude,
        RequestStatus status,
        Boolean active
) {}
