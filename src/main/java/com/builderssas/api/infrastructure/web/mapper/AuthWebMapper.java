package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;

public final class AuthWebMapper {

    private AuthWebMapper() {}

    public static AuthResultDto toAuthResultDto(
            Long userId,
            String username,
            String displayName,
            String role
    ) {
        return new AuthResultDto(
                userId,
                username,
                displayName,
                role,
                true,
                ""
        );
    }

    public static AuthResultDto toRecoverResultDto(
            Long userId,
            String username,
            String displayName,
            String role,
            String tempPassword
    ) {
        return new AuthResultDto(
                userId,
                username,
                displayName,
                role,
                true,
                tempPassword
        );
    }
}
