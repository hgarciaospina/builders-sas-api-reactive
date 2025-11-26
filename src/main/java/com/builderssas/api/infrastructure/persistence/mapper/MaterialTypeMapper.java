package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper funcional encargado de convertir entre:
 *  • MaterialTypeEntity  (infraestructura)
 *  • MaterialTypeRecord  (dominio)
 *
 * Características:
 *  • 100% inmutable.
 *  • 0% setters.
 *  • Sin lógica de negocio.
 *  • Conversión estricta 1:1 entre capas.
 */
@Component
public class MaterialTypeMapper {

    /**
     * Convierte una entity de infraestructura a un record de dominio.
     *
     * @param entity entidad persistida
     * @return record de dominio
     */
    public MaterialTypeRecord toRecord(MaterialTypeEntity entity) {
        return new MaterialTypeRecord(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getUnitOfMeasure(),
                entity.getActive()
        );
    }

    /**
     * Convierte un record de dominio a una entity inmutable para persistencia.
     *
     * @param record record de dominio
     * @return entity lista para guardar con R2DBC
     */
    public MaterialTypeEntity toEntity(MaterialTypeRecord record) {
        return new MaterialTypeEntity(
                record.id(),
                record.code(),
                record.name(),
                record.unitOfMeasure(),
                record.active()
        );
    }
}
