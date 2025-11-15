package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.port.in.constructionorder.GetConstructionOrderUseCase;
import com.builderssas.api.domain.port.in.constructionorder.ListConstructionOrdersUseCase;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionOrderWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador Web encargado de exponer operaciones de consulta
 * relacionadas con órdenes de construcción. Las órdenes no se crean desde
 * la API; únicamente pueden ser consultadas.
 *
 * Este controlador cumple los principios de arquitectura hexagonal:
 * - No contiene lógica de negocio.
 * - Delegación total a casos de uso del dominio.
 * - Adapta la salida mediante DTOs específicos para la capa web.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class ConstructionOrderController {

    private final GetConstructionOrderUseCase getUseCase;
    private final ListConstructionOrdersUseCase listUseCase;
    private final ConstructionOrderWebMapper webMapper;

    /**
     * Obtiene una orden por su identificador.
     *
     * @param id identificador de la orden
     * @return DTO de respuesta con los datos de la orden
     */
    @GetMapping("/{id}")
    public Mono<ConstructionOrderResponseDto> findById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(webMapper::toResponse);
    }

    /**
     * Lista todas las órdenes registradas.
     *
     * @return flujo de órdenes convertidas a DTO para respuesta HTTP
     */
    @GetMapping
    public Flux<ConstructionOrderResponseDto> findAll() {
        return listUseCase.listAll()
                .map(webMapper::toResponse);
    }
}
