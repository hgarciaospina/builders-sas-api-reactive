package com.builderssas.api.domain.model.constructionorder;

public record ConstructionOrder(Long id, Long requestId, String status, Integer dayCount, boolean active) {
}
