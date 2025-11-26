package com.builderssas.api.infrastructure.web.controller.constructiontypematerial;

import com.builderssas.api.domain.port.in.constructiontypematerial.*;
import com.builderssas.api.infrastructure.web.dto.constructiontypematerial.*;
import com.builderssas.api.infrastructure.web.mapper.ConstructionTypeMaterialWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/construction-type-materials")
@RequiredArgsConstructor
public final class ConstructionTypeMaterialController {

    private final CreateConstructionTypeMaterialUseCase createUseCase;
    private final UpdateConstructionTypeMaterialUseCase updateUseCase;
    private final GetConstructionTypeMaterialUseCase getUseCase;
    private final ListConstructionTypeMaterialUseCase listUseCase;
    private final ListActiveConstructionTypeMaterialUseCase listActiveUseCase;
    private final DeleteConstructionTypeMaterialUseCase deleteUseCase;

    @PostMapping
    public Mono<ConstructionTypeMaterialResponseDto> create(
            @Valid @RequestBody ConstructionTypeMaterialCreateDto dto
    ) {
        return Mono.just(dto)
                .map(ConstructionTypeMaterialWebMapper::toDomain)
                .flatMap(createUseCase::create)
                .map(ConstructionTypeMaterialWebMapper::toResponse);
    }

    @PutMapping
    public Mono<ConstructionTypeMaterialResponseDto> update(
            @Valid @RequestBody ConstructionTypeMaterialUpdateDto dto
    ) {
        return Mono.just(dto)
                .map(ConstructionTypeMaterialWebMapper::toDomain)
                .flatMap(record ->
                        updateUseCase.update(dto.id(), record)   // ← ESTA ES LA FIRMA CORRECTA
                )
                .map(ConstructionTypeMaterialWebMapper::toResponse);
    }


    @GetMapping("/{id}")
    public Mono<ConstructionTypeMaterialResponseDto> findById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(ConstructionTypeMaterialWebMapper::toResponse);
    }

    @GetMapping
    public Flux<ConstructionTypeMaterialResponseDto> listAll() {
        return listUseCase.findAll()
                .map(ConstructionTypeMaterialWebMapper::toResponse);
    }

    @GetMapping("/active")
    public Flux<ConstructionTypeMaterialResponseDto> listActive() {
        return listActiveUseCase.listActive()
                .map(ConstructionTypeMaterialWebMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
