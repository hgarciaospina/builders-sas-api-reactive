package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.UpdateConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateConstructionTypeService implements UpdateConstructionTypeUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Mono<ConstructionTypeRecord> update(Long id, ConstructionTypeRecord command) {

        ConstructionTypeRecord updated = new ConstructionTypeRecord(
                id,
                command.name(),
                command.estimatedDays(),
                command.active()
        );

        return repository.save(updated);
    }
}
