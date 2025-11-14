package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import com.builderssas.api.domain.port.in.materialtype.UpdateMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.MaterialTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateMaterialTypeService implements UpdateMaterialTypeUseCase {

    private final MaterialTypeRepository repository;

    @Override
    public Mono<MaterialType> update(Long id, MaterialType command) {
        MaterialType updated = new MaterialType(
            id,
            name, unit, active
        );
        return repository.save(updated);
    }
}
