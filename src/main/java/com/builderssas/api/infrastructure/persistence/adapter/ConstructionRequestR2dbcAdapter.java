package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
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


private ConstructionRequest toDomain(ConstructionRequestEntity e) {
    if (e == null) return null;
    return new ConstructionRequest(
        e.getId(),
            e.getProjectId(),
        e.getConstructionTypeId(),
        e.getLatitude(),
        e.getLongitude(),
        e.getStatus(),
        e.getActive()
    );
}

private ConstructionRequestEntity toEntity(ConstructionRequest d) {
    if (d == null) return null;
    ConstructionRequestEntity e = new ConstructionRequestEntity();
    e.setId(d.id());
    e.setProjectId(d.projectId());
    e.setConstructionTypeId(d.constructionTypeId());
    e.setLatitude(d.latitude());
    e.setLongitude(d.longitude());
    e.setStatus(d.status());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<ConstructionRequest> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionRequest> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionRequest> save(ConstructionRequest aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
