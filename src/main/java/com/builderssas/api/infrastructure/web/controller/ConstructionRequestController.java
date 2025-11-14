package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.UpdateConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/constructionrequests")
@RequiredArgsConstructor
public class ConstructionRequestController {

    private final CreateConstructionRequestUseCase createUseCase;
    private final UpdateConstructionRequestUseCase updateUseCase;
    private final GetConstructionRequestUseCase getUseCase;
    private final ListConstructionRequestsUseCase listUseCase;

    @GetMapping
    public Flux<ConstructionRequest> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<ConstructionRequest> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConstructionRequest> create(@RequestBody ConstructionRequest body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<ConstructionRequest> update(@PathVariable Long id, @RequestBody ConstructionRequest body) {
        return updateUseCase.update(id, body);
    }
}
