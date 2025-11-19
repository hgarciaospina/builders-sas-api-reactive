package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.UpdateMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para actualizar tipos de material.
 */
@Service
@RequiredArgsConstructor
public class UpdateMaterialTypeService implements UpdateMaterialTypeUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Mono<MaterialTypeRecord> update(Long id, MaterialTypeRecord command) {
        return repository.findById(id)
                .filter(MaterialTypeRecord::active)
                .flatMap(existing -> repository.save(command));
    }
}
