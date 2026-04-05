package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;

import java.util.List;

/**
 * Mapper de capa web.
 *
 * 100% funcional:
 * - Sin lógica imperativa
 * - Sin transformación destructiva
 * - Sin pérdida de información
 */
public final class AuthWebMapper {

    private AuthWebMapper() {
    }

    /**
     * LOGIN / AUTH SUCCESS
     */
    public static AuthResultDto toAuthResultDto(
            Long userId,
            String username,
            String displayName,
            List<String> roles,
            String accessToken,
            String refreshToken
    ) {
        return new AuthResultDto(
                userId,
                username,
                displayName,
                roles,
                true,
                null,
                accessToken,
                refreshToken
        );
    }

    /**
     * REFRESH (solo access token)
     */
    public static AuthResultDto toRefreshResultDto(String accessToken) {
        return new AuthResultDto(
                null,
                null,
                null,
                null,
                true,
                null,
                accessToken,
                null
        );
    }

    /**
     * RECOVER PASSWORD
     */
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
                List.of(role),
                false,
                tempPassword,
                null,
                null
        );
    }
}