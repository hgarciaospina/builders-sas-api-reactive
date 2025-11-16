package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Adaptador R2DBC para la entidad {@link RoleEntity}.
 *
 * Este componente forma parte de la capa de infraestructura dentro de la
 * Arquitectura Hexagonal. Su responsabilidad es estrictamente técnica:
 *
 *   • Convertir entre Entity ↔ Record del dominio
 *   • Delegar operaciones al repositorio reactivo R2DBC
 *
 * No implementa ninguna lógica de negocio.
 */
@Component
@RequiredArgsConstructor
public class RoleR2dbcAdapter implements RoleRepositoryPort {

    /** Repositorio R2DBC encargado del acceso a la base de datos. */
    private final RoleR2dbcRepository repository;

    // ============================================================================
    // MAPPERS — funcionales, sin imperativa, consistentes con el dominio
    // ============================================================================

    /**
     * Convierte una {@link RoleEntity} en un {@link RoleRecord}.
     *
     * @param e entidad persistida
     * @return instancia del dominio o null
     */
    private RoleRecord toDomain(RoleEntity e) {
        return Optional.ofNullable(e)
                .map(x -> new RoleRecord(
                        x.getId(),
                        x.getName(),
                        x.getDescription(),
                        x.getActive()
                ))
                .orElse(null);
    }

    /**
     * Convierte un {@link RoleRecord} en una {@link RoleEntity}.
     *
     * @param d record del dominio
     * @return entity equivalente o null
     */
    private RoleEntity toEntity(RoleRecord d) {
        return Optional.ofNullable(d)
                .map(x -> {
                    RoleEntity e = new RoleEntity();
                    e.setId(x.id());
                    e.setName(x.name());
                    e.setDescription(x.description());
                    e.setActive(x.active());
                    return e;
                })
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    /**
     * Busca un rol por su ID.
     *
     * @param id identificador único
     * @return Mono con el record del dominio
     */
    @Override
    public Mono<RoleRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Obtiene todos los roles registrados en el sistema.
     *
     * @return Flux con todos los roles
     */
    @Override
    public Flux<RoleRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    /**
     * Guarda o actualiza un rol.
     *
     * @param aggregate record del dominio
     * @return Mono con la instancia del dominio persistida
     */
    @Override
    public Mono<RoleRecord> save(RoleRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
