package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.infrastructure.persistence.entity.AuthUserEntity;

/**
 * Mapper de infraestructura entre AuthUserEntity y AuthUserRecord.
 *
 * Responsabilidad única:
 * - Transformar datos entre capas.
 *
 * Reglas:
 * - No valida null.
 * - No contiene lógica de negocio.
 * - No contiene control de flujo imperativo.
 *
 * La ausencia de datos se maneja en el flujo reactivo,
 * no dentro del mapper.
 */
public final class AuthUserMapper {

    private AuthUserMapper() {
        // Utility class
    }

    /**
     * Convierte una entidad persistente en modelo de dominio.
     */
    public static AuthUserRecord toDomain(AuthUserEntity entity) {
        return new AuthUserRecord(
                entity.getId(),
                entity.getUserId(),
                entity.getPasswordHash(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Convierte un modelo de dominio en entidad persistente.
     */
    public static AuthUserEntity toEntity(AuthUserRecord record) {
        return new AuthUserEntity(
                record.id(),
                record.userId(),
                record.passwordHash(),
                record.active(),
                record.createdAt(),
                record.updatedAt()
        );
    }
}
