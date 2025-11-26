package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para la tabla "projects".
 *
 * Contiene únicamente los métodos necesarios para:
 * - Validación de unicidad (name, code)
 * - Consulta de activos (borrado lógico)
 * - Soft delete (active = false)
 *
 * No incluye lógica ni reglas de negocio.
 */
public interface ProjectR2dbcRepository extends ReactiveCrudRepository<ProjectEntity, Long> {

    Mono<Boolean> existsByCode(String code);

    Mono<Boolean> existsByName(String name);

    /**
     * Lista solo los proyectos activos.
     */
    @Query("SELECT * FROM projects WHERE active = TRUE")
    Flux<ProjectEntity> findAllActive();

    /**
     * Borrado lógico: se marca active = FALSE.
     */
    @Query("UPDATE projects SET active = FALSE WHERE id = :id")
    Mono<Void> softDelete(Long id);

    /**
     * Secuencia nativa sin huecos.
     * Genera un registro en project_sequence y retorna el número.
     */
    @Query("SELECT COUNT(*) + 1 FROM projects")
    Mono<Long> nextProjectCode();

}
