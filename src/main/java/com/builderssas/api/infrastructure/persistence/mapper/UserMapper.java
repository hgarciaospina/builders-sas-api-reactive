package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.infrastructure.persistence.entity.UserEntity;
import com.builderssas.api.infrastructure.web.dto.user.UserDto;

/**
 * Mapper funcional e inmutable encargado de convertir entre:
 * - UserEntity (infraestructura/persistencia)
 * - UserRecord (dominio)
 * - UserDto (exposición Web)
 *
 * No contiene lógica de negocio ni validaciones.
 * Cumple estrictamente con las reglas de Builders-SAS:
 * - 100% funcional
 * - 0% imperativa
 * - sin condiciones, sin null-checks, sin setters
 */
public final class UserMapper {

    private UserMapper() {
        // Constructor privado para cumplir regla de clase de utilidades puras
    }

    /**
     * Convierte una entidad de infraestructura a un modelo de dominio.
     *
     * @param entity UserEntity proveniente de persistencia.
     * @return UserRecord para capa de dominio.
     */
    public static UserRecord toDomain(UserEntity entity) {
        return new UserRecord(
                entity.getId(),
                entity.getUsername(),
                entity.getDisplayName(),
                entity.getEmail(),
                entity.getActive()
        );
    }

    /**
     * Convierte un record de dominio a una entidad de infraestructura.
     *
     * @param record modelo de dominio.
     * @return entidad inmutable UserEntity para persistencia.
     */
    public static UserEntity toEntity(UserRecord record) {
        return new UserEntity(
                record.id(),
                record.username(),
                record.displayName(),
                record.email(),
                record.active()
        );
    }

    /**
     * Convierte un modelo de dominio a un DTO para exposición Web.
     *
     * @param record modelo de dominio UserRecord.
     * @return UserDto para el controller.
     */
    public static UserDto toDto(UserRecord record) {
        return new UserDto(
                record.id(),
                record.username(),
                record.displayName(),
                record.email(),
                record.active()
        );
    }
}
