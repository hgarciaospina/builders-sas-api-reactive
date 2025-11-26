package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.UserMapper;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC responsable de la comunicación con la base de datos
 * para operaciones relacionadas con usuarios.
 *
 * Cumple con las reglas de Builders-SAS:
 * - 100% funcional (sin imperative code)
 * - Entidades inmutables
 * - Mapper funcional UserMapper
 * - Sin null-checks ni Optional
 * - Sin setters ni mutaciones
 * - Retornos Reactivos (Mono/Flux)
 */
@Component
@RequiredArgsConstructor
public class UserR2dbcAdapter implements UserRepositoryPort {

    private final UserR2dbcRepository repository;

    @Override
    public Mono<UserRecord> save(UserRecord record) {
        return repository.save(UserMapper.toEntity(record))
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<UserRecord> findById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDomain);
    }

    /**
     * Lista TODOS los usuarios (activos + inactivos),
     * ordenados (active DESC, username ASC)
     */
    @Override
    public Flux<UserRecord> findAll() {
        return repository.findAll()
                .map(UserMapper::toDomain);
    }

    /**
     * Lista solo usuarios activos.
     */
    @Override
    public Flux<UserRecord> findAllActive() {
        return repository.findAllActive()
                .map(UserMapper::toDomain);
    }

    /**
     * Lista solo usuarios inactivos (soft-delete).
     */
    @Override
    public Flux<UserRecord> findAllInactive() {
        return repository.findAllInactive()
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<UserRecord> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<UserRecord> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<UserRecord> update(UserRecord record) {
        return repository.save(UserMapper.toEntity(record))
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.softDelete(id)
                .then();
    }
}
