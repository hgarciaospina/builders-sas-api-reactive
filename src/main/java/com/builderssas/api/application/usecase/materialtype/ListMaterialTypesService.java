package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.ListMaterialTypesUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para listar todos los materiales (activos + inactivos).
 */
@Service
@RequiredArgsConstructor
public class ListMaterialTypesService implements ListMaterialTypesUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Flux<MaterialTypeRecord> listAll() {
        return repository.findAll();
    }
}
