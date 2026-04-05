package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.model.enums.TokenType;
import com.builderssas.api.infrastructure.persistence.entity.AuthTokenEntity;

/**
 * Mapper de infraestructura entre AuthTokenEntity y AuthTokenRecord.
 *
 * Responsabilidad:
 * - Adaptar datos entre la capa de persistencia y el dominio
 *
 * Reglas:
 * - No contiene lógica de negocio
 * - No valida nulls (eso se maneja en el flujo reactivo)
 * - No contiene control de flujo imperativo
 *
 * Conversión importante:
 * - tokenType se transforma entre String (DB) y TokenType (dominio)
 */
public final class AuthTokenMapper {

    private AuthTokenMapper() {
        // Utility class
    }

    /**
     * Convierte entidad persistente a modelo de dominio.
     *
     * @param entity entidad de base de datos
     * @return modelo de dominio
     */
    public static AuthTokenRecord toDomain(AuthTokenEntity entity) {
        return new AuthTokenRecord(
                entity.getId(),
                entity.getUserId(),
                entity.getToken(),
                TokenType.valueOf(entity.getTokenType()), // String -> Enum
                entity.getIssuedAt(),
                entity.getExpiresAt(),
                Boolean.TRUE.equals(entity.getRevoked()),
                entity.getCreatedAt()
        );
    }

    /**
     * Convierte modelo de dominio a entidad persistente.
     *
     * @param record modelo de dominio
     * @return entidad para persistencia
     */
    public static AuthTokenEntity toEntity(AuthTokenRecord record) {
        return new AuthTokenEntity(
                record.id(),
                record.userId(),
                record.token(),
                record.tokenType().name(), // Enum -> String
                record.issuedAt(),
                record.expiresAt(),
                record.revoked(),
                record.createdAt()
        );
    }
}