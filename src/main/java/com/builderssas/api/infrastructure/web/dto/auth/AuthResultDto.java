package com.builderssas.api.infrastructure.web.dto.auth;

public record AuthResultDto(
        Long userId,
        String username,
        String displayName,
        String role,
        Boolean authenticated,
         String tempPassword
) {}
