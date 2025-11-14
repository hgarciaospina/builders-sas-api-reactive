package com.builderssas.api.domain.model.constructionrequest;

public record ConstructionRequest(Long id, Long projectId, Long constructionTypeId, Double latitude, Double longitude, String status, boolean active) {
}
