package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import com.builderssas.api.domain.port.in.materialtype.CreateMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.MaterialTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateMaterialTypeService implements CreateMaterialTypeUseCase {

    private final MaterialTypeRepository repository;

    @Override
    public Mono<MaterialType> create(MaterialType command) {
        return repository.save(command);
    }
}
