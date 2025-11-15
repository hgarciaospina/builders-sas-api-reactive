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

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConstructionRequestR2dbcAdapter implements ConstructionRequestRepository {

    private final ConstructionRequestR2dbcRepository repository;

    // ----------- MAPEO FUNCIONAL -----------
    private RequestStatus mapStatus(String value) {
        return Optional.ofNullable(value)
                .map(RequestStatus::valueOf)
                .orElse(null);
    }

    private String mapStatus(RequestStatus status) {
        return Optional.ofNullable(status)
                .map(Enum::name)
                .orElse(null);
    }

    // ---------- ENTITY → DOMAIN ------------
    private ConstructionRequestRecord toDomain(ConstructionRequestEntity e) {
        return Optional.ofNullable(e)
                .map(it -> new ConstructionRequestRecord(
                        it.getId(),
                        it.getProjectId(),
                        it.getConstructionTypeId(),
                        it.getLatitude(),
                        it.getLongitude(),
                        mapStatus(it.getStatus()),
                        it.getActive()
                ))
                .orElse(null);
    }

    // ---------- DOMAIN → ENTITY ------------
    private ConstructionRequestEntity toEntity(ConstructionRequestRecord d) {
        return Optional.ofNullable(d)
                .map(it -> {
                    ConstructionRequestEntity e = new ConstructionRequestEntity();
                    e.setId(it.id());
                    e.setProjectId(it.projectId());
                    e.setConstructionTypeId(it.constructionTypeId());
                    e.setLatitude(it.latitude());
                    e.setLongitude(it.longitude());
                    e.setStatus(mapStatus(it.status()));
                    e.setActive(it.active());
                    return e;
                })
                .orElse(null);
    }

    // ------------ OPERACIONES --------------
    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
