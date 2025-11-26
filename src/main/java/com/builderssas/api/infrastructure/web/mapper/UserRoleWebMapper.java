package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.infrastructure.web.dto.userrole.CreateUserRoleDto;
import com.builderssas.api.infrastructure.web.dto.userrole.UpdateUserRoleDto;
import com.builderssas.api.infrastructure.web.dto.userrole.UserRoleDto;

/**
 * Mapper para convertir entre DTOs de la capa Web
 * y records de dominio relacionados con UserRole.
 *
 * Esta clase no contiene lógica de negocio:
 * - CreateUserRoleDto → UserRoleRecord (campos audit se generan en casos de uso)
 * - UpdateUserRoleDto → UserRoleRecord (solo id y active se usan)
 * - UserRoleRecord → UserRoleDto (salida al cliente)
 */
public final class UserRoleWebMapper {

    private UserRoleWebMapper() {}

    /**
     * Convierte un DTO de creación a un record de dominio.
     */
    public static UserRoleRecord toDomain(CreateUserRoleDto dto) {
        return new UserRoleRecord(
                null,                // id generado por DB
                dto.userId(),
                dto.roleId(),
                null,                // assignedAt → caso de uso
                null,                // createdAt  → caso de uso
                null,                // updatedAt  → caso de uso
                true                 // siempre activo al crear
        );
    }

    /**
     * Convierte un DTO de actualización a un record de dominio.
     *
     * Este record se envía al caso de uso,
     * que se encarga de definir updatedAt y mantener invariantes.
     */
    public static UserRoleRecord toDomain(UpdateUserRoleDto dto, UserRoleRecord existing) {
        return new UserRoleRecord(
                existing.id(),
                existing.userId(),
                existing.roleId(),
                existing.assignedAt(),
                existing.createdAt(),
                existing.updatedAt(),
                dto.active()
        );
    }

    /**
     * Convierte un record de dominio a DTO de salida.
     */
    public static UserRoleDto toDto(UserRoleRecord record) {
        return new UserRoleDto(
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
