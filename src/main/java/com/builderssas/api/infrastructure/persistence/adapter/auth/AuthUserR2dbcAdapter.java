package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.AuthUserEntity;
import com.builderssas.api.infrastructure.persistence.mapper.AuthUserMapper;
import com.builderssas.api.infrastructure.persistence.repository.AuthUserR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthUserR2dbcAdapter implements AuthUserRepositoryPort {

    private final AuthUserR2dbcRepository repository;

    @Override
    public Mono<AuthUserRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(AuthUserMapper::toDomain);
    }

    @Override
    public Mono<AuthUserRecord> save(AuthUserRecord authUser) {
        AuthUserEntity entity = AuthUserMapper.toEntity(authUser);
        return repository.save(entity)
                .map(AuthUserMapper::toDomain);
    }
}
