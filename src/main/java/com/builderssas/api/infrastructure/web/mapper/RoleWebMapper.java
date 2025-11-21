package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.infrastructure.web.dto.role.CreateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.UpdateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.RoleResponseDto;

/**
 * Mapper Web estático para el módulo Role.
 *
 * Su función es transformar:
 *  - DTOs → RoleRecord
 *  - RoleRecord → DTOs
 *
 * Sin lógica de negocio, sin validaciones,
 * sin dependencia a Spring, sin inyección.
 */
public final class RoleWebMapper {

    private RoleWebMapper() {}

    /**
     * Transformación del DTO de creación hacia el record de dominio.
     * - id = null (lo asigna persistencia)
     * - active = true en creación
     */
    public static RoleRecord toDomain(CreateRoleDto dto) {
        return new RoleRecord(
                null,
                dto.name(),
                dto.description(),
                true
        );
    }

    /**
     * Transformación del DTO de actualización hacia el record de dominio.
     * - active NO se controla aquí (lo maneja ToggleRoleStatusUseCase)
     */
    public static RoleRecord toDomain(UpdateRoleDto dto) {
        return new RoleRecord(
                dto.id(),
                dto.name(),
                dto.description(),
                null
        );
    }

    /**
     * Transformación del record del dominio hacia el DTO expuesto al cliente.
     */
    public static RoleResponseDto toResponse(RoleRecord record) {
        return new RoleResponseDto(
                record.id(),
                record.name(),
                record.description(),
                record.active()
        );
    }
}
