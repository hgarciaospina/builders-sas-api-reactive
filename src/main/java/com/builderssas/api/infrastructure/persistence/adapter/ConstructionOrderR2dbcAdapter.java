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
 * Adaptador de persistencia R2DBC para la entidad ConstructionOrder.
 *
 * Su responsabilidad es conectar el dominio con la infraestructura reactiva,
 * garantizando una transformación pura y funcional entre:
 *
 *     • ConstructionOrderEntity  ↔  ConstructionOrderRecord
 *
 * Este adaptador:
 *   - No contiene lógica imperativa.
 *   - No ejecuta validaciones de negocio.
 *   - No muta estado.
 *   - Se limita a delegar operaciones al repositorio reactivo
 *     y aplicar mapeo funcional mediante ConstructionOrderMapper.
 *
 * Cumple estrictamente los principios de Arquitectura Hexagonal.
 */
@Component
@RequiredArgsConstructor
public class ConstructionOrderR2dbcAdapter implements ConstructionOrderRepository {

    private final ConstructionOrderR2dbcRepository repository;
    private final ConstructionOrderMapper mapper;

    /**
     * Recupera una orden por su identificador.
     *
     * @param id identificador único de la orden
     * @return Mono con la orden encontrada o vacío si no existe
     */
    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes asociadas a un proyecto.
     *
     * @param projectId identificador del proyecto
     * @return Flux con todas las órdenes del proyecto
     */
    @Override
    public Flux<ConstructionOrderRecord> findByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId)
                .map(mapper::toRecord);
    }

    /**
     * Recupera la última orden registrada según fecha de finalización programada.
     *
     * @param projectId identificador del proyecto
     * @return Mono con la última orden o vacío si no existen registros
     */
    @Override
    public Mono<ConstructionOrderRecord> findLastByProjectId(Long projectId) {
        return repository.findTopByProjectIdOrderByScheduledEndDateDesc(projectId)
                .map(mapper::toRecord);
    }

    /**
     * Recupera todas las órdenes almacenadas.
     *
     * @return Flux con todas las órdenes del sistema
     */
    @Override
    public Flux<ConstructionOrderRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Persiste o actualiza una orden de construcción.
     *
     * @param aggregate instancia inmutable del dominio
     * @return Mono con la orden persistida convertida nuevamente a record
     */
    @Override
    public Mono<ConstructionOrderRecord> save(ConstructionOrderRecord aggregate) {
        return Mono.justOrEmpty(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }
}
