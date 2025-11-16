package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.in.materialtype.CreateMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.UpdateMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.GetMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.ListMaterialTypesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/materialtypes")
@RequiredArgsConstructor
public class MaterialTypeController {

    private final CreateMaterialTypeUseCase createUseCase;
    private final UpdateMaterialTypeUseCase updateUseCase;
    private final GetMaterialTypeUseCase getUseCase;
    private final ListMaterialTypesUseCase listUseCase;

    @GetMapping
    public Flux<MaterialTypeRecord> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<MaterialTypeRecord> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MaterialTypeRecord> create(@RequestBody MaterialTypeRecord body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<MaterialTypeRecord> update(@PathVariable Long id, @RequestBody MaterialTypeRecord body) {
        return updateUseCase.update(id, body);
    }
}
