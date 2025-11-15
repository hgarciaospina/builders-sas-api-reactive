package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.CreateConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateConstructionTypeService implements CreateConstructionTypeUseCase {

    private final ConstructionTypeRepository repository;

    @Override
    public Mono<ConstructionTypeRecord> create(ConstructionTypeRecord command) {
        return repository.save(command);
    }
}
