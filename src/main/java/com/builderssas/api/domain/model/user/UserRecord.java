package com.builderssas.api.domain.model.user;

public record UserRecord(Long id, String username, String displayName, String email, Boolean active) {
}
