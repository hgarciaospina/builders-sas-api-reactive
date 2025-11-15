package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.GetConstructionOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class ConstructionOrderController {

    private final GetConstructionOrderUseCase getUseCase;
    private final CreateConstructionOrderUseCase createUseCase;

    // GET ALL
    @GetMapping
    public Flux<ConstructionOrderRecord> findAll() {
        return getUseCase.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Mono<ConstructionOrderRecord> findById(@PathVariable Long id) {
        return getUseCase.findById(id);
    }

    // CREATE (solo para pruebas, normalmente la crea la solicitud)
    @PostMapping
    public Mono<ConstructionOrderRecord> create(@RequestBody ConstructionOrderRecord request) {
        return createUseCase.create(request);
    }
}
