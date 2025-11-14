package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import com.builderssas.api.domain.port.in.materialtype.GetMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.MaterialTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetMaterialTypeService implements GetMaterialTypeUseCase {

    private final MaterialTypeRepository repository;

    @Override
    public Mono<MaterialType> getById(Long id) {
        return repository.findById(id);
    }
}
