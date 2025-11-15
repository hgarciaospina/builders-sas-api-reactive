package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.material.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.ListMaterialTypesUseCase;
import com.builderssas.api.domain.port.out.MaterialTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListMaterialTypesService implements ListMaterialTypesUseCase {

    private final MaterialTypeRepository repository;

    @Override
    public Flux<MaterialTypeRecord> listAll() {
        return repository.findAll();
    }
}
