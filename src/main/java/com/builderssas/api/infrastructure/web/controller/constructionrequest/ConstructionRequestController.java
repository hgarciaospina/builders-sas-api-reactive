package com.builderssas.api.infrastructure.web.controller.constructionrequest;

import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionRequestWebMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador WebFlux para gestionar solicitudes de construcción.
 *
 * - Recibe peticiones
 * - Valida DTOs
 * - Llama casos de uso
 * - Convierte DTO ↔ Record mediante WebMapper estático
 *
 * No contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/v1/construction-requests")
public class ConstructionRequestController {

    private final CreateConstructionRequestUseCase createUseCase;
    private final GetConstructionRequestUseCase getUseCase;
    private final ListConstructionRequestsUseCase listUseCase;

    public ConstructionRequestController(
            CreateConstructionRequestUseCase createUseCase,
            GetConstructionRequestUseCase getUseCase,
            ListConstructionRequestsUseCase listUseCase
    ) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    @PostMapping
    public Mono<ConstructionRequestResponseDto> create(
            @Valid @RequestBody ConstructionRequestCreateDto dto
    ) {
        return Mono.just(dto)
                .map(ConstructionRequestWebMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(ConstructionRequestWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ConstructionRequestResponseDto> findById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(ConstructionRequestWebMapper::toResponse);
    }

    @GetMapping
    public Flux<ConstructionRequestResponseDto> listAll() {
        return listUseCase.listAll()
                .map(ConstructionRequestWebMapper::toResponse);
    }
}
