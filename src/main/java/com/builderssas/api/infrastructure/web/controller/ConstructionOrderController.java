package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.UpdateConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.GetConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.ListConstructionOrdersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/constructionorders")
@RequiredArgsConstructor
public class ConstructionOrderController {

    private final CreateConstructionOrderUseCase createUseCase;
    private final UpdateConstructionOrderUseCase updateUseCase;
    private final GetConstructionOrderUseCase getUseCase;
    private final ListConstructionOrdersUseCase listUseCase;

    @GetMapping
    public Flux<ConstructionOrder> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<ConstructionOrder> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ConstructionOrder> create(@RequestBody ConstructionOrder body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<ConstructionOrder> update(@PathVariable Long id, @RequestBody ConstructionOrder body) {
        return updateUseCase.update(id, body);
    }
}
