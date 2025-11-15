package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.domain.port.out.ConstructionRequestRepository;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionRequestEntity;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionRequestR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para la entidad ConstructionRequest, encargado de
 * conectar el dominio con la capa de persistencia reactiva.
 *
 * Este componente implementa el puerto de salida definido por el dominio
 * y realiza el mapeo bidireccional entre la entidad persistente y el
 * record de dominio.
 */
@Component
@RequiredArgsConstructor
public class ConstructionRequestR2dbcAdapter implements ConstructionRequestRepository {

    private final ConstructionRequestR2dbcRepository repository;

    /**
     * Convierte una entidad reactiva en un record de dominio.
     *
     * @param e entidad persistente ConstructionRequestEntity
     * @return instancia de ConstructionRequestRecord
     */
    private ConstructionRequestRecord toRecord(ConstructionRequestEntity e) {
        return new ConstructionRequestRecord(
                e.getId(),
                e.getProjectId(),
                e.getConstructionTypeId(),
                e.getLatitude(),
                e.getLongitude(),
                e.getRequestedByUserId(),
                e.getRequestDate(),
                mapStatus(e.getRequestStatus()),
                e.getObservations(),
                e.isActive()
        );
    }

    /**
     * Convierte un record de dominio en una entidad persistente.
     *
     * @param r ConstructionRequestRecord a convertir
     * @return instancia de ConstructionRequestEntity
     */
    private ConstructionRequestEntity toEntity(ConstructionRequestRecord r) {
        ConstructionRequestEntity e = new ConstructionRequestEntity();
        e.setId(r.id());
        e.setProjectId(r.projectId());
        e.setConstructionTypeId(r.constructionTypeId());
        e.setLatitude(r.latitude());
        e.setLongitude(r.longitude());
        e.setRequestedByUserId(r.requestedByUserId());
        e.setRequestDate(r.requestDate());
        e.setRequestStatus(mapStatus(r.requestStatus()));
        e.setObservations(r.observations());
        e.setActive(r.active());
        return e;
    }

    /**
     * Convierte una cadena almacenada en base de datos hacia un valor del enum RequestStatus.
     *
     * @param value cadena representando el estado
     * @return RequestStatus correspondiente o null si es nulo
     */
    private RequestStatus mapStatus(String value) {
        return value == null ? null : RequestStatus.valueOf(value);
    }

    /**
     * Convierte un enum RequestStatus en su representación en texto para la base de datos.
     *
     * @param status valor del enum
     * @return representación en texto o null si es nulo
     */
    private String mapStatus(RequestStatus status) {
        return status == null ? null : status.name();
    }

    /**
     * Recupera una solicitud por identificador.
     *
     * @param id identificador de la solicitud
     * @return Mono con la solicitud encontrada o vacío
     */
    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id)
                .map(this::toRecord);
    }

    /**
     * Recupera todas las solicitudes registradas.
     *
     * @return Flux con todas las solicitudes persistidas
     */
    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll()
                .map(this::toRecord);
    }

    /**
     * Persiste una solicitud nueva o actualizada.
     *
     * @param record instancia del record de dominio
     * @return Mono con la entidad persistida convertida nuevamente a record
     */
    @Override
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord record) {
        return repository.save(toEntity(record))
                .map(this::toRecord);
    }

    /**
     * Recupera todas las solicitudes asociadas a un usuario específico.
     *
     * @param userId identificador del usuario solicitante
     * @return Flux con las solicitudes encontradas
     */
    @Override
    public Flux<ConstructionRequestRecord> findByRequestedByUserId(Long userId) {
        return repository.findByRequestedByUserId(userId)
                .map(this::toRecord);
    }

    /**
     * Recupera todas las solicitudes pertenecientes a un proyecto determinado.
     *
     * @param projectId identificador del proyecto
     * @return Flux con las solicitudes encontradas
     */
    @Override
    public Flux<ConstructionRequestRecord> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId)
                .map(this::toRecord);
    }
}
