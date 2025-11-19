package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventory.InventoryMovementRecord;
import com.builderssas.api.domain.port.out.inventorymovement.InventoryMovementRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.InventoryMovementMapper;
import com.builderssas.api.infrastructure.persistence.repository.InventoryMovementR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador de persistencia R2DBC para movimientos de inventario.
 *
 * Implementa el puerto de salida InventoryMovementRepositoryPort,
 * delegando en InventoryMovementR2dbcRepository para el acceso a
 * la base de datos y utilizando InventoryMovementMapper para
 * convertir entre el modelo de dominio y la entidad persistente.
 *
 * Reglas:
 * - Sin programación imperativa.
 * - Sin if/else, sin ternarios.
 * - Sin uso de Optional.map.orElse.
 * - Uso de pipelines reactivos con Mono y Flux.
 */
@Component
@RequiredArgsConstructor
public class InventoryMovementR2dbcAdapter implements InventoryMovementRepositoryPort {

    private final InventoryMovementR2dbcRepository repository;

    /**
     * Registra un movimiento de inventario en la base de datos.
     */
    @Override
    public Mono<InventoryMovementRecord> save(InventoryMovementRecord movement) {
        return Mono.just(movement)
                .map(InventoryMovementMapper::toEntity)
                .flatMap(repository::save)
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Recupera todos los movimientos de inventario.
     */
    @Override
    public Flux<InventoryMovementRecord> findAll() {
        return repository.findAll()
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Busca un movimiento por su identificador.
     */
    @Override
    public Mono<InventoryMovementRecord> findById(Long id) {
        return repository.findById(id)
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Recupera todos los movimientos relacionados con un material.
     */
    @Override
    public Flux<InventoryMovementRecord> findByMaterialId(Long materialId) {
        return repository.findByMaterialId(materialId)
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Recupera todos los movimientos relacionados con una orden.
     */
    @Override
    public Flux<InventoryMovementRecord> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(InventoryMovementMapper::toDomain);
    }

    /**
     * Recupera todos los movimientos asociados a un usuario específico.
     *
     * Este método implementa el contrato del dominio y delega la consulta
     * directamente al repositorio R2DBC, convirtiendo el resultado a
     * InventoryMovementRecord mediante el mapper.
     *
     * @param userId identificador del usuario
     * @return flujo reactivo de movimientos
     */
    @Override
    public Flux<InventoryMovementRecord> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(InventoryMovementMapper::toDomain);
    }
}
