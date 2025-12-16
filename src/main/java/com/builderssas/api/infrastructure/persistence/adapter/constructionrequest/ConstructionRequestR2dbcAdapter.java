package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionRequestMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionRequestR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador de persistencia R2DBC para solicitudes de construcción.
 *
 * Este componente implementa el puerto de salida
 * {@link ConstructionRequestRepositoryPort} siguiendo estrictamente
 * la Arquitectura Hexagonal y las reglas del proyecto:
 *
 *  • 0% imperativa (sin if/else, sin setters, sin mutación).
 *  • Entidades y records inmutables.
 *  • Mapeo puro mediante ConstructionRequestMapper.
 *  • Interacción reactiva usando Mono/Flux (WebFlux - no blocking).
 *  • Sin lógica de negocio — solo persistencia y mapeo.
 */
@Component
@RequiredArgsConstructor
public class ConstructionRequestR2dbcAdapter implements ConstructionRequestRepositoryPort {

    private final ConstructionRequestR2dbcRepository repository;
    private final ConstructionRequestMapper mapper;

    /**
     * Obtiene una solicitud por su ID.
     *
     * @param id identificador único
     * @return Mono con el record encontrado o vacío si no existe
     */
    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return Mono.just(id)
                .flatMap(repository::findById)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las solicitudes almacenadas.
     *
     * @return Flux con todos los records de dominio
     */
    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Persiste o actualiza una solicitud de construcción.
     *
     * @param aggregate record de dominio a almacenar
     * @return Mono con la versión persistida
     */
    @Override
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord aggregate) {
        return Mono.just(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    /**
     * Obtiene todas las solicitudes creadas por un usuario.
     *
     * @param userId identificador del usuario creador
     * @return Flux con los records asociados
     */
    @Override
    public Flux<ConstructionRequestRecord> findByRequestedByUserId(Long userId) {
        return Mono.just(userId)
                .flatMapMany(repository::findByRequestedByUserId)
                .map(mapper::toRecord);
    }

    /**
     * Obtiene todas las solicitudes asociadas a un proyecto.
     *
     * @param projectId identificador del proyecto
     * @return Flux con los records del dominio
     */
    @Override
    public Flux<ConstructionRequestRecord> findByProjectId(Long projectId) {
        return Mono.just(projectId)
                .flatMapMany(repository::findByProjectId)
                .map(mapper::toRecord);
    }
}
