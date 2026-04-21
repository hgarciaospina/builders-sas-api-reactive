package com.builderssas.api.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitud de renovación de access token.
 *
 * @param refreshToken JWT de tipo REFRESH sin prefijo Bearer
 */
public record RefreshRequestDto(@NotBlank String refreshToken) {}