package com.builderssas.api.infrastructure.web.controller.inventory;

import com.builderssas.api.application.usecase.inventory.InventoryInputService;
import com.builderssas.api.application.usecase.inventory.InventoryOutputService;
import com.builderssas.api.application.usecase.inventory.InventoryService;
import com.builderssas.api.domain.model.inventory.InventoryRecord;
import com.builderssas.api.infrastructure.web.dto.inventory.InventoryInputDto;
import com.builderssas.api.infrastructure.web.dto.inventory.InventoryOutputDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * InventoryController
 *
 * Controller responsable de exponer los endpoints públicos para:
 *
 *  • Entradas de inventario   (INPUT)
 *  • Salidas de inventario    (OUTPUT)
 *  • Consulta de stock vivo   (GET)
 *
 * Reglas:
 *  • 100% WebFlux (Mono)
 *  • Sin lógica de negocio (delegado a los servicios)
 *  • Sin creación de DTOs innecesarios
 *  • No expone InventoryMovementRecord ni InventoryRecord a capa Web
 *  • El userId siempre proviene del header "X-User-Id"
 *
 * Arquitectura:
 *  • Los servicios Input/Output solo manejan reglas de inventario.
 *  • InventoryService maneja el stock vivo.
 *  • Este controller solo orquesta llamadas.
 */
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryInputService inputService;
    private final InventoryOutputService outputService;
    private final InventoryService inventoryService;

    // ============================================================
    //                 REGISTER INVENTORY INPUT  (IN)
    // ============================================================
    @PostMapping("/input")
    public Mono<Void> registerInput(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody InventoryInputDto dto
    ) {
        return inputService.registerInput(
                dto.materialId(),
                dto.quantity(),
                dto.reason(),
                userId
        );
    }

    // ============================================================
    //                 REGISTER INVENTORY OUTPUT (OUT)
    // ============================================================
    @PostMapping("/output")
    public Mono<Void> registerOutput(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody InventoryOutputDto dto
    ) {
        return outputService.registerOutput(
                dto.materialId(),
                dto.quantity(),
                dto.reason(),
                userId
        );
    }

    // ============================================================
    //                   GET LIVE STOCK BY MATERIAL
    // ============================================================
    @GetMapping("/{materialId}")
    public Mono<Double> getStock(
            @PathVariable Long materialId
    ) {
        return inventoryService.findByMaterialId(materialId)
                .map(InventoryRecord::stock);
    }
}
