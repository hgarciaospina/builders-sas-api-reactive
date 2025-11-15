package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.CreateConstructionTypeUseCase;
import com.builderssas.api.domain.port.in.constructiontype.UpdateConstructionTypeUseCase;
import com.builderssas.api.domain.port.in.constructiontype.GetConstructionTypeUseCase;
import com.builderssas.api.domain.port.in.constructiontype.ListConstructionTypesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/constructiontypes")
@RequiredArgsConstructor
public class ConstructionTypeController {

    private final CreateConstructionTypeUseCase createUseCase;
    private final UpdateConstructionTypeUseCase updateUseCase;
    private final GetConstructionTypeUseCase getUseCase;
    private final ListConstructionTypesUseCase listUseCase;

    @GetMapping
    public Flux<ConstructionTypeRecord> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<ConstructionTypeRecord> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConstructionTypeRecord> create(@RequestBody ConstructionTypeRecord body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<ConstructionTypeRecord> update(@PathVariable Long id, @RequestBody ConstructionTypeRecord body) {
        return updateUseCase.update(id, body);
    }
}
