package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.material.MaterialTypeRecord;
import com.builderssas.api.domain.port.out.MaterialTypeRepository;
import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import com.builderssas.api.infrastructure.persistence.repository.MaterialTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MaterialTypeR2dbcAdapter implements MaterialTypeRepository {

    private final MaterialTypeR2dbcRepository repository;


private MaterialTypeRecord toDomain(MaterialTypeEntity e) {
    if (e == null) return null;
    return new MaterialTypeRecord(
        e.getId(),
            e.getName(),
        e.getUnit(),
        e.getActive()
    );
}

private MaterialTypeEntity toEntity(MaterialTypeRecord d) {
    if (d == null) return null;
    MaterialTypeEntity e = new MaterialTypeEntity();
    e.setId(d.id());
    e.setName(d.name());
    e.setUnit(d.unit());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<MaterialTypeRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<MaterialTypeRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<MaterialTypeRecord> save(MaterialTypeRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
