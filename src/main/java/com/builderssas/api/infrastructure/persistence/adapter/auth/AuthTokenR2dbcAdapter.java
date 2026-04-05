package com.builderssas.api.infrastructure.persistence.adapter.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.AuthTokenMapper;
import com.builderssas.api.infrastructure.persistence.repository.AuthTokenR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Adapter R2DBC que implementa el puerto AuthTokenRepositoryPort.
 */
@Component
@RequiredArgsConstructor
public class AuthTokenR2dbcAdapter implements AuthTokenRepositoryPort {

    private final AuthTokenR2dbcRepository repository;

    @Override
    public Mono<AuthTokenRecord> save(AuthTokenRecord token) {
        return Mono.just(token)
                .map(AuthTokenMapper::toEntity)
                .flatMap(repository::save)
                .map(AuthTokenMapper::toDomain);
    }

    @Override
    public Mono<AuthTokenRecord> findByToken(String token) {
        return repository.findByToken(token)
                .map(AuthTokenMapper::toDomain);
    }

    @Override
    public Mono<Boolean> isTokenActive(String token, LocalDateTime now) {
        return repository.isTokenActive(token, now)
                .defaultIfEmpty(false);
    }
}
