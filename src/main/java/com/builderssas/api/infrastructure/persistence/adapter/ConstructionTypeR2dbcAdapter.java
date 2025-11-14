package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.construction.ConstructionType;
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


private ConstructionType toDomain(ConstructionTypeEntity e) {
    if (e == null) return null;
    return new ConstructionType(
        e.getId(),
            e.getName(),
        e.getEstimatedDays(),
        e.getActive()
    );
}

private ConstructionTypeEntity toEntity(ConstructionType d) {
    if (d == null) return null;
    ConstructionTypeEntity e = new ConstructionTypeEntity();
    e.setId(d.id());
    e.setName(d.name());
    e.setEstimatedDays(d.estimatedDays());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<ConstructionType> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionType> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionType> save(ConstructionType aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
