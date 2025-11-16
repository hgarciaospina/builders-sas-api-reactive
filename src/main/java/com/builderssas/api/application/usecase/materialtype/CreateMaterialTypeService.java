package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.CreateMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateMaterialTypeService implements CreateMaterialTypeUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Mono<MaterialTypeRecord> create(MaterialTypeRecord command) {
        return repository.save(command);
    }
}
