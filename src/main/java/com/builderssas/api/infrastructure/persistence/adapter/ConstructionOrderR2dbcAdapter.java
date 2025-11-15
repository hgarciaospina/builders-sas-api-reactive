package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.out.ConstructionOrderRepository;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionOrderMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionOrderR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador encargado de conectar el dominio con la infraestructura R2DBC
 * para operaciones sobre órdenes de construcción. Su rol consiste en:
 * - Delegar operaciones al repositorio reactivo.
 * - Convertir entre entity ↔ record utilizando el mapper funcional.
 *
 * No contiene lógica de negocio, ni lógica imperativa.
 */
@Component
@RequiredArgsConstructor
public class ConstructionOrderR2dbcAdapter implements ConstructionOrderRepository {

    private final ConstructionOrderR2dbcRepository repository;
    private final ConstructionOrderMapper mapper;

    /**
     * Obtiene una orden por su identificador.
     *
     * @param id identificador de la orden
     * @return orden equivalente en el dominio
     */
    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes asociadas a un proyecto.
     *
     * @param projectId id del proyecto
     * @return flujo de órdenes del dominio
     */
    @Override
    public Flux<ConstructionOrderRecord> findByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId)
                .map(mapper::toRecord);
    }

    /**
     * Recupera la última orden basada en la fecha de finalización programada.
     *
     * @param projectId id del proyecto
     * @return última orden encontrada
     */
    @Override
    public Mono<ConstructionOrderRecord> findLastByProjectId(Long projectId) {
        return repository.findTopByProjectIdOrderByScheduledEndDateDesc(projectId)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes almacenadas.
     *
     * @return flujo de todas las órdenes
     */
    @Override
    public Flux<ConstructionOrderRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Persiste una orden mediante R2DBC.
     *
     * @param aggregate record del dominio
     * @return orden persistida retornada como record
     */
    @Override
    public Mono<ConstructionOrderRecord> save(ConstructionOrderRecord aggregate) {
        return Mono.justOrEmpty(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }
}
