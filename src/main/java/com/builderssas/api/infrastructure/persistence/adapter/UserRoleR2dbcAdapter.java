package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para la persistencia de asignaciones Usuario–Rol.
 *
 * Implementa el puerto de salida UserRoleRepositoryPort dentro de la
 * Arquitectura Hexagonal. Su función es convertir entre:
 *
 *   • UserRoleRecord (dominio, inmutable)
 *   • UserRoleEntity (infraestructura, mutable técnica)
 *
 * No contiene lógica de negocio.
 * Su único objetivo es mantener una transición limpia y funcional
 * entre el dominio y la base de datos.
 */
@Component
@RequiredArgsConstructor
public class UserRoleR2dbcAdapter implements UserRoleRepositoryPort {

    private final UserRoleR2dbcRepository repository;

    // -------------------------------------------------------------------------
    // Mapeo: Entity → Record
    // -------------------------------------------------------------------------

    private UserRoleRecord toDomain(UserRoleEntity e) {
        return new UserRoleRecord(
                e.getId(),
                e.getUserId(),
                e.getRoleId(),
                e.getAssignedAt(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getActive()
        );
    }

    // -------------------------------------------------------------------------
    // Mapeo: Record → Entity (sin programación imperativa)
    // -------------------------------------------------------------------------

    private UserRoleEntity toEntity(UserRoleRecord r) {
        return new UserRoleEntity(
                r.id(),
                r.userId(),
                r.roleId(),
                r.assignedAt(),
                r.createdAt(),
                r.updatedAt(),
                r.active()
        );
    }

    // -------------------------------------------------------------------------
    // Implementación del Puerto OUT
    // -------------------------------------------------------------------------

    @Override
    public Mono<UserRoleRecord> save(UserRoleRecord record) {
        return Mono.just(record)
                .map(this::toEntity)
                .flatMap(repository::save)
                .map(this::toDomain);
    }

    @Override
    public Mono<UserRoleRecord> findById(Long id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(this::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findByRoleId(Long roleId) {
        return repository.findByRoleId(roleId)
                .map(this::toDomain);
    }

    @Override
    public Flux<UserRoleRecord> findAll() {
        return repository.findAll()
                .map(this::toDomain);
    }
}
