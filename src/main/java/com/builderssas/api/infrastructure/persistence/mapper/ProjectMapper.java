package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.infrastructure.persistence.entity.ProjectEntity;

/**
 * Mapper para conversión entre:
 * - ProjectEntity (infraestructura / persistencia)
 * - ProjectRecord (dominio)
 *
 * Estándar Builders-SAS:
 * - Clase final
 * - Métodos estáticos
 * - Constructor privado
 * - Conversión estricta campo a campo
 * - Sin lógica de negocio
 * - Sin imperativa
 */
public final class ProjectMapper {

    private ProjectMapper() {

    }

    /**
     * Convierte una entidad persistente a un record del dominio.
     *
     * @param entity entidad persistida ProjectEntity
     * @return ProjectRecord del dominio
     */
    public static ProjectRecord toDomain(ProjectEntity entity) {
        return new ProjectRecord(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProgressPercentage(),
                entity.getStatus(),
                entity.getObservations(),
                entity.getCreatedByUserId(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Convierte un record del dominio a una entidad persistente inmutable.
     *
     * @param record ProjectRecord del dominio
     * @return ProjectEntity para persistencia
     */
    public static ProjectEntity toEntity(ProjectRecord record) {
        return new ProjectEntity(
                record.id(),
                record.name(),
                record.code(),
                record.description(),
                record.startDate(),
                record.endDate(),
                record.progressPercentage(),
                record.status(),
                record.observations(),
                record.createdByUserId(),
                record.active(),
                record.createdAt(),
                record.updatedAt()
        );
    }
}
