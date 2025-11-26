package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.GetMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener un tipo de material por su ID.
 * Filtra automáticamente solo activos.
 */
@Service
@RequiredArgsConstructor
public class GetMaterialTypeService implements GetMaterialTypeUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Mono<MaterialTypeRecord> getById(Long id) {
        return repository.findById(id)
                .filter(MaterialTypeRecord::active);
    }
}
