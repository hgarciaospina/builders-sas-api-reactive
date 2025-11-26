package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionRequestEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Mapper funcional para convertir entre:
 *  - ConstructionRequestEntity (infraestructura)
 *  - ConstructionRequestRecord (dominio)
 *
 * 100% inmutable, sin programación imperativa
 * y completamente alineado a los tipos reales del dominio y la tabla.
 */
@Component
public class ConstructionRequestMapper {

    /**
     * Convierte una entity de infraestructura a un record de dominio.
     */
    public ConstructionRequestRecord toRecord(ConstructionRequestEntity e) {
        return new ConstructionRequestRecord(
                e.getId(),
                e.getProjectId(),
                e.getConstructionTypeId(),
                e.getLatitude(),
                e.getLongitude(),
                e.getRequestedByUserId(),
                mapToLocalDate(e.getRequestDate()),
                mapToEnum(e.getRequestStatus()),
                e.getObservations(),
                Optional.ofNullable(e.getActive()).orElse(Boolean.FALSE)
        );
    }

    /**
     * Convierte un record de dominio a una entity inmutable.
     */
    public ConstructionRequestEntity toEntity(ConstructionRequestRecord r) {
        return new ConstructionRequestEntity(
                r.id(),
                r.projectId(),
                r.constructionTypeId(),
                r.latitude(),
                r.longitude(),
                r.requestedByUserId(),
                mapToLocalDateTime(r.requestDate()),
                mapToString(r.requestStatus()),
                r.observations(),
                Boolean.valueOf(r.active())
        );
    }

    // -------------------- Conversión funcional pura --------------------

    private RequestStatus mapToEnum(String value) {
        return Optional.ofNullable(value)
                .map(RequestStatus::valueOf)
                .orElse(null);
    }

    private String mapToString(RequestStatus status) {
        return Optional.ofNullable(status)
                .map(Enum::name)
                .orElse(null);
    }

    private LocalDate mapToLocalDate(LocalDateTime dt) {
        return Optional.ofNullable(dt)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }

    private LocalDateTime mapToLocalDateTime(LocalDate date) {
        return Optional.ofNullable(date)
                .map(LocalDate::atStartOfDay)
                .orElse(null);
    }
}
