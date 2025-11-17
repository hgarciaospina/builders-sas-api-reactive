package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Mapper responsable de convertir entre los DTO utilizados en la capa web y
 * los records del dominio relacionados con ConstructionRequest.
 *
 * Este componente no contiene lógica de negocio; únicamente transforma datos
 * entre las estructuras expuestas al cliente (DTOs) y el modelo interno del dominio.
 *
 * Las conversiones realizadas son completamente funcionales y no emplean
 * programación imperativa.
 */
@Component
public class ConstructionRequestWebMapper {

    /**
     * Convierte un DTO de creación proveniente de la capa web en un record del dominio.
     * Los valores generados automáticamente dentro del sistema se asignan aquí de forma
     * predeterminada siguiendo las reglas establecidas.
     *
     * @param dto datos de entrada desde la petición web
     * @return instancia de ConstructionRequestRecord preparada para la capa de aplicación
     */
    public ConstructionRequestRecord toRecord(ConstructionRequestCreateDto dto) {
        return new ConstructionRequestRecord(
                null,                                   // id generado en persistencia
                dto.projectId(),                        // projectId
                dto.constructionTypeId(),               // constructionTypeId
                dto.latitude(),                         // latitude
                dto.longitude(),                        // longitude
                null,                                   // requestedByUserId (lo asigna seguridad)
                LocalDate.now(),                        // requestDate generada automáticamente
                RequestStatus.PENDING,                  // estado inicial de la solicitud
                null,                                   // observations
                Boolean.TRUE                            // active
        );
    }

    /**
     * Convierte un record del dominio en un DTO de respuesta que será expuesto a la capa web.
     *
     * @param record instancia proveniente del dominio
     * @return DTO de salida para consumidores externos
     */
    public ConstructionRequestResponseDto toResponse(ConstructionRequestRecord record) {
        return new ConstructionRequestResponseDto(
                record.id(),
                record.projectId(),
                record.constructionTypeId(),
                record.latitude(),
                record.longitude(),
                record.requestedByUserId(),
                record.requestDate(),
                record.requestStatus(),
                record.observations(),
                record.active()
        );
    }
}
