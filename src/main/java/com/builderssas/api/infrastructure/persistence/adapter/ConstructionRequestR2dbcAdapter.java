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

@Component
@RequiredArgsConstructor
public class ConstructionRequestR2dbcAdapter implements ConstructionRequestRepository {

    private final ConstructionRequestR2dbcRepository repository;

    /**
     * Mapea entidad → record
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
                mapStatusToEnum(e.getRequestStatus()),
                e.getObservations(),
                e.isActive()
        );
    }

    /**
     * Mapea record → entidad sin usar setters imperativos
     */
    private ConstructionRequestEntity toEntity(ConstructionRequestRecord r) {

        return Mono.just(new ConstructionRequestEntity())
                .map(e -> {
                    e.setId(r.id());
                    e.setProjectId(r.projectId());
                    e.setConstructionTypeId(r.constructionTypeId());
                    e.setLatitude(r.latitude());
                    e.setLongitude(r.longitude());
                    e.setRequestedByUserId(r.requestedByUserId());
                    e.setRequestDate(r.requestDate());
                    e.setRequestStatus(mapStatusToString(r.requestStatus()));
                    e.setObservations(r.observations());
                    e.setActive(r.active());
                    return e;
                })
                .block();
    }

    /**
     * String → Enum
     */
    private RequestStatus mapStatusToEnum(String value) {
        return Mono.justOrEmpty(value)
                .map(RequestStatus::valueOf)
                .blockOptional()
                .orElse(null);
    }

    /**
     * Enum → String
     */
    private String mapStatusToString(RequestStatus status) {
        return Mono.justOrEmpty(status)
                .map(RequestStatus::name)
                .blockOptional()
                .orElse(null);
    }

    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id).map(this::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll().map(this::toRecord);
    }

    @Override
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord record) {
        return Mono.just(record)
                .map(this::toEntity)
                .flatMap(repository::save)
                .map(this::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findByRequestedByUserId(Long userId) {
        return repository.findByRequestedByUserId(userId)
                .map(this::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId)
                .map(this::toRecord);
    }
}
