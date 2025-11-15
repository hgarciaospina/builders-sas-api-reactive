package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.out.RoleRepository;
import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoleR2dbcAdapter implements RoleRepository {

    private final RoleR2dbcRepository repository;


private RoleRecord toDomain(RoleEntity e) {
    if (e == null) return null;
    return new RoleRecord(
        e.getId(),
            e.getName(),
        e.getDescription(),
        e.getActive()
    );
}

private RoleEntity toEntity(RoleRecord d) {
    if (d == null) return null;
    RoleEntity e = new RoleEntity();
    e.setId(d.id());
    e.setName(d.name());
    e.setDescription(d.description());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<RoleRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<RoleRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<RoleRecord> save(RoleRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
