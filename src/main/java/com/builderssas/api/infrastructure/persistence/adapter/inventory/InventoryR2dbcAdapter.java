package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.domain.port.out.inventory.InventoryRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.InventoryMapper;
import com.builderssas.api.infrastructure.persistence.repository.InventoryR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryR2dbcAdapter implements InventoryRepositoryPort {

    private final InventoryR2dbcRepository repository;

    @Override
    public Mono<InventoryRecord> findByMaterialId(Long materialId) {
        return repository.findByMaterialId(materialId)
                .map(InventoryMapper::toDomain);
    }

    @Override
    public Flux<InventoryRecord> findAll() {
        return repository.findAll()
                .map(InventoryMapper::toDomain);
    }

    @Override
    public Mono<InventoryRecord> save(InventoryRecord record) {
        return Mono.just(record)
                .map(InventoryMapper::toEntity)
                .flatMap(repository::save)
                .map(InventoryMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByMaterialId(Long materialId) {
        return repository.deleteByMaterialId(materialId);
    }
}
