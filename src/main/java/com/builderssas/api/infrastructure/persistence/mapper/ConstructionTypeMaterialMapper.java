package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeMaterialEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper funcional encargado de convertir entre:
 *
 * • ConstructionTypeMaterialRecord (dominio)
 * • ConstructionTypeMaterialEntity (infraestructura/R2DBC)
 *
 * Este componente forma parte de la capa de Infraestructura dentro de la
 * Arquitectura Hexagonal. No contiene lógica de negocio.
 *
 * Características:
 * • 100% funcional (sin mutaciones ni setters).
 * • Conversión directa campo a campo.
 * • Consistencia con el estándar utilizado en MaterialTypeMapper.
 */
@Component
public class ConstructionTypeMaterialMapper {

    /**
     * Convierte una entidad R2DBC en un record de dominio.
     *
     * @param entity instancia persistente
     * @return record de dominio
     */
    public ConstructionTypeMaterialRecord toRecord(ConstructionTypeMaterialEntity entity) {
        return new ConstructionTypeMaterialRecord(
                entity.getId(),
                entity.getConstructionTypeId(),
                entity.getMaterialTypeId(),
                entity.getQuantityRequired(),
                entity.getActive()
        );
    }

    /**
     * Convierte un record de dominio en una entidad R2DBC.
     *
     * @param record record de dominio
     * @return entidad persistente lista para almacenamiento
     */
    public ConstructionTypeMaterialEntity toEntity(ConstructionTypeMaterialRecord record) {
        return new ConstructionTypeMaterialEntity(
                record.id(),
                record.constructionTypeId(),
                record.materialTypeId(),
                record.quantityRequired(),
                record.active()
        );
    }
}
