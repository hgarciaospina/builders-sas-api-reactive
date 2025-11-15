package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ConstructionTypeR2dbcAdapter implements ConstructionTypeRepository {

    private final ConstructionTypeR2dbcRepository repository;


private ConstructionTypeRecord toDomain(ConstructionTypeEntity e) {
    if (e == null) return null;
    return new ConstructionTypeRecord(
        e.getId(),
            e.getName(),
        e.getEstimatedDays(),
        e.getActive()
    );
}

private ConstructionTypeEntity toEntity(ConstructionTypeRecord d) {
    if (d == null) return null;
    ConstructionTypeEntity e = new ConstructionTypeEntity();
    e.setId(d.id());
    e.setName(d.name());
    e.setEstimatedDays(d.estimatedDays());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<ConstructionTypeRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionTypeRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionTypeRecord> save(ConstructionTypeRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
