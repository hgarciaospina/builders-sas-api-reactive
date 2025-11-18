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
 * órdenes de construcción. Su única responsabilidad es recibir
 * solicitudes HTTP, delegar en los casos de uso correctos y
 * convertir los resultados mediante el WebMapper.
 *
 * No contiene lógica de negocio ni reglas del dominio.
 */
@RestController
@RequestMapping("/api/v1/construction-orders")
public class ConstructionOrderController {

    private final CreateConstructionOrderUseCase createUseCase;
    private final GetConstructionOrderUseCase getUseCase;
    private final ListConstructionOrdersUseCase listUseCase;
    private final ConstructionOrderWebMapper webMapper;

    public ConstructionOrderController(
            CreateConstructionOrderUseCase createUseCase,
            GetConstructionOrderUseCase getUseCase,
            ListConstructionOrdersUseCase listUseCase,
            ConstructionOrderWebMapper webMapper
    ) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.webMapper = webMapper;
    }

    /**
     * Recupera una orden de construcción por su ID.
     *
     * @param id identificador de la orden
     * @return representación DTO de la orden
     */
    @GetMapping("/{id}")
    public Mono<ConstructionOrderResponseDto> getById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(webMapper::toResponse);
    }

    /**
     * Lista todas las órdenes registradas.
     *
     * @return flujo con las órdenes convertidas a DTO
     */
    @GetMapping
    public Flux<ConstructionOrderResponseDto> findAll() {
        return listUseCase.listAll()
                .map(webMapper::toResponse);
    }

    @PostMapping
    public Mono<ConstructionOrderResponseDto> create(
            @RequestBody CreateConstructionOrderRequestDto request
    ) {
        return Mono.just(request)
                .map(webMapper::toDomain)   //DTO → RECORD de dominio
                .flatMap(createUseCase::create) // Use case recibe record
                .map(webMapper::toResponse);    //  RECORD → DTO respuesta
    }
}
