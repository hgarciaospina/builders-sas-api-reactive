package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.infrastructure.persistence.entity.AuthUserEntity;

public final class AuthUserMapper {

    private AuthUserMapper() {
    }

    public static AuthUserRecord toDomain(AuthUserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new AuthUserRecord(
                entity.getId(),
                entity.getUserId(),
                entity.getPasswordHash(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static AuthUserEntity toEntity(AuthUserRecord record) {
        if (record == null) {
            return null;
        }

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
