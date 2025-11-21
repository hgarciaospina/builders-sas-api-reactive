package com.builderssas.api.infrastructure.web.controller.constructionorder;

import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.GetConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.ListConstructionOrdersUseCase;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import com.builderssas.api.infrastructure.web.dto.constructionorder.CreateConstructionOrderRequestDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionOrderWebMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador encargado de exponer los endpoints relacionados con
 * órdenes de construcción.
 *
 * - No contiene lógica de negocio.
 * - No toca infraestructura.
 * - Convierte DTO ↔ Record usando WebMapper estático.
 */
@RestController
@RequestMapping("/api/v1/construction-orders")
public class ConstructionOrderController {

    private final CreateConstructionOrderUseCase createUseCase;
    private final GetConstructionOrderUseCase getUseCase;
    private final ListConstructionOrdersUseCase listUseCase;

    public ConstructionOrderController(
            CreateConstructionOrderUseCase createUseCase,
            GetConstructionOrderUseCase getUseCase,
            ListConstructionOrdersUseCase listUseCase
    ) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    /**
     * GET /construction-orders/{id}
     * Obtiene una orden por su ID.
     */
    @GetMapping("/{id}")
    public Mono<ConstructionOrderResponseDto> getById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(ConstructionOrderWebMapper::toResponse);
    }

    /**
     * GET /construction-orders
     * Lista todas las órdenes.
     */
    @GetMapping
    public Flux<ConstructionOrderResponseDto> findAll() {
        return listUseCase.listAll()
                .map(ConstructionOrderWebMapper::toResponse);
    }

    /**
     * POST /construction-orders
     * Crea una orden de construcción.
     */
    @PostMapping
    public Mono<ConstructionOrderResponseDto> create(
            @RequestBody CreateConstructionOrderRequestDto request
    ) {
        return Mono.just(request)
                .map(ConstructionOrderWebMapper::toDomain)
                .flatMap(createUseCase::create)
                .map(ConstructionOrderWebMapper::toResponse);
    }
}
