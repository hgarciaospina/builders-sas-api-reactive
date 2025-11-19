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

/**
 * Controlador WebFlux encargado de exponer las operaciones CRUD
 * para tipos de material dentro de la Arquitectura Hexagonal.
 *
 * Solo recibe peticiones HTTP, delega en casos de uso y convierte
 * DTOs ↔ Records mediante el WebMapper.
 *
 * No contiene lógica de negocio.
 */
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
    private final MaterialTypeWebMapper webMapper;

    /**
     * Crea un nuevo tipo de material.
     */
    @PostMapping
    public Mono<MaterialTypeResponseDto> create(
            @Valid @RequestBody MaterialTypeCreateDto dto
    ) {
        return Mono.just(dto)
                .map(webMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(webMapper::toResponse);
    }

    /**
     * Consulta un tipo de material activo por ID.
     */
    @GetMapping("/{id}")
    public Mono<MaterialTypeResponseDto> getById(@PathVariable Long id) {
        return getUseCase.getById(id)
                .map(webMapper::toResponse);
    }

    /**
     * Lista únicamente materiales activos.
     */
    @GetMapping
    public Flux<MaterialTypeResponseDto> listActive() {
        return listActiveUseCase.listActive()
                .map(webMapper::toResponse);
    }

    /**
     * Lista todos los materiales (activos + inactivos).
     */
    @GetMapping("/all")
    public Flux<MaterialTypeResponseDto> listAll() {
        return listAllUseCase.listAll()
                .map(webMapper::toResponse);
    }

    /**
     * Actualiza un tipo de material.
     */
    @PutMapping("/{id}")
    public Mono<MaterialTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MaterialTypeUpdateDto dto
    ) {
        return Mono.just(dto)
                .map(d -> webMapper.toRecord(id, d))
                .flatMap(r -> updateUseCase.update(id, r))
                .map(webMapper::toResponse);
    }

    /**
     * Borrado lógico.
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
