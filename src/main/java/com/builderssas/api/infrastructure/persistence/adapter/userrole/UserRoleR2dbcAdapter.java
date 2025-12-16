package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;
import com.builderssas.api.infrastructure.persistence.mapper.UserRoleMapper;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para las operaciones de persistencia de UserRole.
 *
 * Sigue exactamente el estándar del UserR2dbcAdapter:
 *  - Cada método delega directamente al repositorio R2DBC.
 *  - Conversión entre Entity y Record mediante UserRoleMapper.
 *  - No contiene lógica de negocio.
 */
@Component
@RequiredArgsConstructor
public class UserRoleR2dbcAdapter implements UserRoleRepositoryPort {

    private final UserRoleR2dbcRepository repository;

    @Override
    public Mono<UserRoleRecord> save(UserRoleRecord record) {
        UserRoleEntity entity = UserRoleMapper.toEntity(record);
        return repository.save(entity)
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Mono<UserRoleRecord> findById(Long id) {
        return repository.findById(id)
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findAll() {
        return repository.findAll()
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findAllActive() {
        return repository.findAllActive()
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findAllInactive() {
        return repository.findAllInactive()
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.softDelete(id);
    }

    @Override
    public Flux<UserRoleRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(UserRoleMapper::toDomain);
    }

    @Override
    public Mono<UserRoleRecord> findByUserIdAndRoleId(Long userId, Long roleId) {
        return repository.findByUserIdAndRoleId(userId, roleId)
                .map(UserRoleMapper::toDomain);
    }
}
