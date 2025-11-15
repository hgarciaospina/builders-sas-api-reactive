package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/construction-requests")
@RequiredArgsConstructor
public class ConstructionRequestController {

    private final CreateConstructionRequestUseCase createUseCase;
    private final GetConstructionRequestUseCase getUseCase;
    private final ListConstructionRequestsUseCase listUseCase;

    // LIST ALL
    @GetMapping
    public Flux<ConstructionRequestRecord> listAll() {
        return listUseCase.listAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Mono<ConstructionRequestRecord> findById(@PathVariable Long id) {
        return getUseCase.findById(id);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConstructionRequestRecord> create(@RequestBody ConstructionRequestRecord body) {
        return createUseCase.create(body);
    }
}
