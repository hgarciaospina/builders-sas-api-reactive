// File: src/main/java/com/builderssas/api/infrastructure/persistence/mapper/UserRoleMapper.java
package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;

/**
 * Mapper entre UserRoleEntity (infraestructura) y UserRoleRecord (dominio).
 *
 * Convenciones oficiales:
 * - toDomain(): Entity → Record (Dominio)
 * - toEntity(): Record → Entity (Infraestructura)
 */
public final class UserRoleMapper {

    private UserRoleMapper() {
    }

    public static UserRoleRecord toDomain(UserRoleEntity entity) {
        return new UserRoleRecord(
                entity.getId(),
                entity.getUserId(),
                entity.getRoleId(),
                entity.getAssignedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getActive()
        );
    }

    public static UserRoleEntity toEntity(UserRoleRecord record) {
        return new UserRoleEntity(
                record.id(),
                record.userId(),
                record.roleId(),
                record.assignedAt(),
                record.createdAt(),
                record.updatedAt(),
                record.active()
        );
    }
}
