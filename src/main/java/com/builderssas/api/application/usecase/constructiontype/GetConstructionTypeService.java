package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.GetConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetConstructionTypeService implements GetConstructionTypeUseCase {

    private final ConstructionTypeRepository repository;

    @Override
    public Mono<ConstructionTypeRecord> getById(Long id) {
        return repository.findById(id);
    }
}
