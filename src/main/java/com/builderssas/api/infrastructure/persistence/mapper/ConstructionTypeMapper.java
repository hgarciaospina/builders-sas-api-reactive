package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de convertir entre:
 *  • ConstructionTypeEntity  (infraestructura)
 *  • ConstructionTypeRecord  (dominio)
 *
 * Conversión 100% funcional, sin mutaciones ni lógica de negocio.
 */
@Component
public class ConstructionTypeMapper {

    /**
     * Convierte una entidad de infraestructura en un record del dominio.
     */
    public ConstructionTypeRecord toRecord(ConstructionTypeEntity e) {
        return new ConstructionTypeRecord(
                e.getId(),
                e.getName(),
                e.getEstimatedDays(),
                e.getActive()
        );
    }

    /**
     * Convierte un record del dominio en una entidad persistente inmutable.
     */
    public ConstructionTypeEntity toEntity(ConstructionTypeRecord r) {
        return new ConstructionTypeEntity(
                r.id(),
                r.name(),
                r.estimatedDays(),
                r.active()
        );
    }
}
