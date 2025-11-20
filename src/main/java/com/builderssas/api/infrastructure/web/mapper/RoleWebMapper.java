package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.infrastructure.web.dto.role.CreateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.UpdateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.RoleResponseDto;

/**
 * Mapper Web → Dominio y Dominio → Web.
 *
 * Este componente:
 * - No contiene lógica de negocio.
 * - No realiza validaciones.
 * - Mantiene inmutabilidad total.
 * - Aísla completamente la capa Web de la capa de dominio.
 *
 * Su responsabilidad exclusiva es transformar:
 *  DTOs → RoleRecord
 *  RoleRecord → DTOs
 *
 * Forma parte de la capa de infraestructura Web dentro de la arquitectura
 * hexagonal definida para el sistema.
 */
public final class RoleWebMapper {

    private RoleWebMapper() {
        // Previene instanciación
    }

    /**
     * Convierte un DTO de creación en un RoleRecord de dominio.
     * - El valor de "active" SIEMPRE es true al crear.
     * - El id es generado por la persistencia.
     *
     * @param dto datos enviados desde la petición web
     * @return RoleRecord listo para la capa de aplicación
     */
    public static RoleRecord toDomain(CreateRoleDto dto) {
        return new RoleRecord(
                null,                   // id generado por persistencia
                dto.name(),
                dto.description(),
                true                    // SIEMPRE true al crear
        );
    }

    /**
     * Convierte un DTO de actualización en un RoleRecord.
     * - No modifica "active" porque el estado pertenece al caso de uso
     *   ToggleRoleStatusUseCase, no a este.
     *
     * @param dto DTO con los datos de actualización
     * @return RoleRecord normalizado para actualizar
     */
    public static RoleRecord toDomain(UpdateRoleDto dto) {
        return new RoleRecord(
                dto.id(),
                dto.name(),
                dto.description(),
                null   // el active REAL lo define la capa de aplicación
        );
    }

    /**
     * Convierte un RoleRecord en un DTO para exponerlo al cliente.
     *
     * @param record entidad del dominio
     * @return DTO serializable para transporte web
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
