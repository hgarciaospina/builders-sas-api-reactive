package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionType;
import com.builderssas.api.domain.port.in.constructiontype.UpdateConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateConstructionTypeService implements UpdateConstructionTypeUseCase {

    private final ConstructionTypeRepository repository;

    @Override
    public Mono<ConstructionType> update(Long id, ConstructionType command) {
        ConstructionType updated = new ConstructionType(
            id,
            name, estimatedDays, active
        );
        return repository.save(updated);
    }
}
