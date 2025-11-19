package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.ListActiveMaterialTypesUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para listar únicamente materiales activos.
 */
@Service
@RequiredArgsConstructor
public class ListActiveMaterialTypesService implements ListActiveMaterialTypesUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Flux<MaterialTypeRecord> listActive() {
        return repository.findAllActive();
    }
}
