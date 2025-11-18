package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionOrderMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionOrderR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para la persistencia de órdenes de construcción
 * dentro de la Arquitectura Hexagonal (WebFlux + R2DBC).
 *
 * Cumple completamente con el puerto de salida:
 * {@link ConstructionOrderRepositoryPort}
 *
 * No usa programación imperativa: cero if/else, cero null, cero setters.
 * Todo es funcional, reactivo y alineado a los repositorios reales.
 */
@Component
@RequiredArgsConstructor
public class ConstructionOrderR2dbcAdapter implements ConstructionOrderRepositoryPort {

    private final ConstructionOrderR2dbcRepository repository;
    private final ConstructionOrderMapper mapper;

    /**
     * Recupera una orden por su id.
     */
    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return Mono.just(id)
                .flatMap(repository::findById)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes asociadas a un proyecto.
     * Port: findByProjectId()
     * Repo real: findAllByProjectId()
     */
    @Override
    public Flux<ConstructionOrderRecord> findByProjectId(Long projectId) {
        return Mono.just(projectId)
                .flatMapMany(repository::findAllByProjectId)
                .map(mapper::toRecord);
    }

    /**
     * Recupera la última orden registrada para un proyecto.
     * Port: findLastByProjectId()
     * Repo real: findTopByProjectIdOrderByScheduledEndDateDesc()
     */
    @Override
    public Mono<ConstructionOrderRecord> findLastByProjectId(Long projectId) {
        return Mono.just(projectId)
                .flatMap(repository::findTopByProjectIdOrderByScheduledEndDateDesc)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes.
     */
    @Override
    public Flux<ConstructionOrderRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Guarda o actualiza una orden de construcción.
     */
    @Override
    public Mono<ConstructionOrderRecord> save(ConstructionOrderRecord aggregate) {
        return Mono.just(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    /**
     * Verifica si existe una orden en una ubicación exacta (latitud + longitud).
     *
     * Port: existsByLatitudeAndLongitude()
     * Repo real debe llamarse: existsByLatitudeAndLongitude()
     *
     * Si tu repo tiene otro nombre, me dices y lo ajusto.
     */
    @Override
    public Mono<Boolean> existsByLatitudeAndLongitude(Double latitude, Double longitude) {
        return Mono.just(latitude)
                .zipWith(Mono.just(longitude))
                .flatMap(coords -> repository.existsByLatitudeAndLongitude(coords.getT1(), coords.getT2()));
    }
}
