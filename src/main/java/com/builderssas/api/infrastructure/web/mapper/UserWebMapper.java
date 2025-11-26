package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.infrastructure.web.dto.user.UserDto;

/**
 * Mapper Web ↔ Dominio para el módulo de usuarios.
 *
 * Responsabilidades:
 * - Convertir UserRecord (dominio) a UserDto (respuesta web).
 * - (Opcional) Convertir UserDto a UserRecord si se requiere en el futuro.
 *
 * No contiene lógica de negocio ni validaciones.
 * Solo transforma estructuras de datos entre capas.
 */
public final class UserWebMapper {

    private UserWebMapper() {
        // Evita instanciación
    }

    /**
     * Convierte un modelo de dominio UserRecord en un DTO expuesto por la capa web.
     *
     * @param record modelo de dominio del usuario
     * @return DTO serializable hacia el cliente
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

    /**
     * Convierte un UserDto en un UserRecord de dominio.
     *
     * Actualmente no es utilizado en los casos de uso,
     * pero se deja disponible para futuros escenarios.
     *
     * @param dto DTO proveniente de la capa web
     * @return modelo de dominio UserRecord
     */
    public static UserRecord toDomain(UserDto dto) {
        return new UserRecord(
                dto.id(),
                dto.username(),
                dto.displayName(),
                dto.email(),
                dto.active()
        );
    }
}
