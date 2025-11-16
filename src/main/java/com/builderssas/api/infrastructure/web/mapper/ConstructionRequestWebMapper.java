package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Mapper entre los DTO expuestos por la capa web y el record del dominio.
 * Su responsabilidad es únicamente adaptar datos sin aplicar lógica de negocio.
 */
@Component
public class ConstructionRequestWebMapper {

    /**
     * Convierte un DTO de creación en un record del dominio.
     *
     * @param dto datos enviados por el cliente
     * @return ConstructionRequestRecord consistente con el modelo del dominio
     */
    public ConstructionRequestRecord toRecord(ConstructionRequestCreateDto dto) {
        return new ConstructionRequestRecord(
                null,                     // id
                dto.projectId(),          // projectId
                dto.constructionTypeId(), // constructionTypeId
                dto.latitude(),           // latitude
                dto.longitude(),          // longitude
                null,                     // requestedByUserId (se integrará con JWT)
                LocalDate.now(),          // requestDate
                null,                     // requestStatus (se asigna después)
                null,                     // observations (se asigna después)
                true                      // active
        );
    }

    /**
     * Convierte un record del dominio en un DTO de salida.
     *
     * @param r record del dominio
     * @return ConstructionRequestResponseDto para respuesta HTTP
     */
    public ConstructionRequestResponseDto toResponse(ConstructionRequestRecord r) {
        return new ConstructionRequestResponseDto(
                r.id(),
                r.projectId(),
                r.constructionTypeId(),
                r.latitude(),
                r.longitude(),
                r.requestedByUserId(),
                r.requestDate(),
                r.requestStatus(),
                r.observations(),
                r.active()
        );
    }
}
