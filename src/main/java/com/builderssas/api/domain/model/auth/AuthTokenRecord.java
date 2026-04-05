package com.builderssas.api.domain.model.auth;

import com.builderssas.api.domain.model.enums.TokenType;

import java.time.LocalDateTime;

/**
 * Immutable domain model representing a persisted authentication token.
 *
 * <p>This record is part of the domain layer and models a token with
 * full lifecycle control, including:
 * <ul>
 *     <li>Server-side expiration validation</li>
 *     <li>Explicit revocation (logout / security events)</li>
 *     <li>Token type differentiation (ACCESS vs REFRESH)</li>
 * </ul>
 *
 * <p>This enables enterprise-grade session management beyond stateless JWT,
 * supporting secure refresh flows and session invalidation.</p>
 *
 * @param id unique identifier of the token record
 * @param userId identifier of the associated user
 * @param token raw token value (typically a JWT)
 * @param tokenType type of token ({@link TokenType#ACCESS} or {@link TokenType#REFRESH})
 * @param issuedAt timestamp when the token was issued
 * @param expiresAt server-side expiration timestamp (source of truth)
 * @param revoked flag indicating whether the token has been invalidated
 * @param createdAt persistence timestamp
 */
public record AuthTokenRecord(
        Long id,
        Long userId,
        String token,
        TokenType tokenType,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt,
        boolean revoked,
        LocalDateTime createdAt
) {

    /**
     * Validates whether the token is currently usable.
     *
     * @return true if the token is not revoked and not expired
     */
    public boolean isValid() {
        return !revoked && expiresAt.isAfter(LocalDateTime.now());
    }

    /**
     * Indicates whether this token is a refresh token.
     *
     * @return true if token type is REFRESH
     */
    public boolean isRefreshToken() {
        return tokenType == TokenType.REFRESH;
    }

    /**
     * Indicates whether this token is an access token.
     *
     * @return true if token type is ACCESS
     */
    public boolean isAccessToken() {
        return tokenType == TokenType.ACCESS;
    }
}