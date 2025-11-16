package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.ProjectEntity;
import com.builderssas.api.infrastructure.persistence.repository.ProjectR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Adaptador R2DBC para la entidad {@link ProjectEntity}.
 *
 * Este componente forma parte de la capa de infraestructura en la
 * Arquitectura Hexagonal. Su misión es puramente técnica:
 *
 *   • Convertir entre Entity ↔ Record del dominio
 *   • Delegar las operaciones a un repositorio R2DBC reactivo
 *
 * No implementa lógica de negocio.
 */
@Component
@RequiredArgsConstructor
public class ProjectR2dbcAdapter implements ProjectRepositoryPort {

    /** Repositorio R2DBC responsable del acceso a la base de datos. */
    private final ProjectR2dbcRepository repository;

    // ============================================================================
    // MAPPERS — 100% funcionales, sin programación imperativa
    // ============================================================================

    /**
     * Convierte una {@link ProjectEntity} en su representación de dominio
     * {@link ProjectRecord}.
     *
     * @param e instancia persistida
     * @return instancia del dominio o null si e es null
     */
    private ProjectRecord toDomain(ProjectEntity e) {
        return Optional.ofNullable(e)
                .map(x -> new ProjectRecord(
                        x.getId(),
                        x.getName(),
                        x.getCode(),
                        x.getActive()
                ))
                .orElse(null);
    }

    /**
     * Convierte un {@link ProjectRecord} en una {@link ProjectEntity}
     * lista para persistencia.
     *
     * @param d record del dominio
     * @return entity equivalente o null si d es null
     */
    private ProjectEntity toEntity(ProjectRecord d) {
        return Optional.ofNullable(d)
                .map(x -> {
                    ProjectEntity e = new ProjectEntity();
                    e.setId(x.id());
                    e.setName(x.name());
                    e.setCode(x.code());
                    e.setActive(x.active());
                    return e;
                })
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    /**
     * Busca un proyecto por su ID.
     *
     * @param id identificador único del proyecto
     * @return Mono con el record del dominio
     */
    @Override
    public Mono<ProjectRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Obtiene todos los proyectos de la base de datos.
     *
     * @return Flux con todos los proyectos registrados
     */
    @Override
    public Flux<ProjectRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    /**
     * Guarda o actualiza un proyecto.
     *
     * @param aggregate record del dominio
     * @return Mono con el record persistido
     */
    @Override
    public Mono<ProjectRecord> save(ProjectRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
