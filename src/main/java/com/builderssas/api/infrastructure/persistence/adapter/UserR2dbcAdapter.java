package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.out.UserRepository;
import com.builderssas.api.infrastructure.persistence.entity.UserEntity;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserR2dbcAdapter implements UserRepository {

    private final UserR2dbcRepository repository;


private UserRecord toDomain(UserEntity e) {
    if (e == null) return null;
    return new UserRecord(
        e.getId(),
            e.getUsername(),
        e.getDisplayName(),
        e.getEmail(),
        e.getActive()
    );
}

private UserEntity toEntity(UserRecord d) {
    if (d == null) return null;
    UserEntity e = new UserEntity();
    e.setId(d.id());
    e.setUsername(d.username());
    e.setDisplayName(d.displayName());
    e.setEmail(d.email());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<UserRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<UserRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<UserRecord> save(UserRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
