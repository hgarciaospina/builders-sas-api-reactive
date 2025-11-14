package com.builderssas.api.domain.model.user;

public record User(Long id, String username, String displayName, String email, boolean active) {
}
