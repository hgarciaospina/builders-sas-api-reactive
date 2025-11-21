package com.builderssas.api.infrastructure.web.controller.materialtype;

import com.builderssas.api.domain.port.in.materialtype.CreateMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.GetMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.ListActiveMaterialTypesUseCase;
import com.builderssas.api.domain.port.in.materialtype.ListMaterialTypesUseCase;
import com.builderssas.api.domain.port.in.materialtype.UpdateMaterialTypeUseCase;
import com.builderssas.api.domain.port.in.materialtype.DeleteMaterialTypeUseCase;

import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeCreateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeUpdateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeResponseDto;

import com.builderssas.api.infrastructure.web.mapper.MaterialTypeWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/material-types")
@RequiredArgsConstructor
public class MaterialTypeController {

    private final CreateMaterialTypeUseCase createUseCase;
    private final GetMaterialTypeUseCase getUseCase;
    private final ListActiveMaterialTypesUseCase listActiveUseCase;
    private final ListMaterialTypesUseCase listAllUseCase;
    private final UpdateMaterialTypeUseCase updateUseCase;
    private final DeleteMaterialTypeUseCase deleteUseCase;

    @PostMapping
    public Mono<MaterialTypeResponseDto> create(
            @Valid @RequestBody MaterialTypeCreateDto dto
    ) {
        return Mono.just(dto)
                .map(MaterialTypeWebMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(MaterialTypeWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<MaterialTypeResponseDto> getById(@PathVariable Long id) {
        return getUseCase.getById(id)
                .map(MaterialTypeWebMapper::toResponse);
    }

    @GetMapping("/active")
    public Flux<MaterialTypeResponseDto> listActive() {
        return listActiveUseCase.listActive()
                .map(MaterialTypeWebMapper::toResponse);
    }

    @GetMapping("/all")
    public Flux<MaterialTypeResponseDto> listAll() {
        return listAllUseCase.listAll()
                .map(MaterialTypeWebMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<MaterialTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MaterialTypeUpdateDto dto
    ) {
        return Mono.just(dto)
                .map(d -> MaterialTypeWebMapper.toRecord(id, d))
                .flatMap(r -> updateUseCase.update(id, r))
                .map(MaterialTypeWebMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
