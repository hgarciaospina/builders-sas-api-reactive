package com.builderssas.api.application.usecase.constructionrequest.stock;

import com.builderssas.api.application.usecase.inventory.InventoryService;
import com.builderssas.api.domain.model.inventory.InventoryRecord;

import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Validates live inventory stock required by a construction type.
 *
 * Responsibilities:
 *  • Load all required materials for a construction type.
 *  • Compare required vs available stock from the "inventory" table.
 *  • Return a StockSummary indicating if all materials are OK.
 *
 * Architectural Notes:
 *  • Pure application-level logic.
 *  • No orchestration, no notifications, no movements.
 *  • Immutable records; fully reactive (Mono/Flux).
 */
@Service
@RequiredArgsConstructor
public class StockValidationService {

    private final ConstructionTypeMaterialRepositoryPort typeMaterialRepo;
    private final InventoryService inventoryService;
    private final MaterialTypeRepositoryPort materialRepo;

    // ============================
    //      INTERNAL RECORDS
    // ============================

    public record MaterialCheck(
            Long materialTypeId,
            String materialName,
            Double required,
            Double available,
            boolean exists,
            boolean ok
    ) {}

    public record StockSummary(
            boolean allOK,
            List<MaterialCheck> items
    ) {}

    // ============================
    //      PUBLIC API
    // ============================

    /**
     * Validates the required materials for a construction type and checks the
     * current inventory stock for each material.
     *
     * @param constructionTypeId required construction type ID
     * @return StockSummary with pass/fail information
     */
    public Mono<StockSummary> validate(Long constructionTypeId) {

        return typeMaterialRepo.findByConstructionTypeId(constructionTypeId)

                .flatMap(rel ->
                        inventoryService.findByMaterialId(rel.materialTypeId())
                                .defaultIfEmpty(new InventoryRecord(
                                        null,
                                        rel.materialTypeId(),
                                        0.0,
                                        null
                                ))
                                .map(inv ->
                                        new MaterialCheck(
                                                rel.materialTypeId(),
                                                null,                       // filled later
                                                rel.quantityRequired(),
                                                inv.stock(),
                                                inv.stock() > 0,
                                                inv.stock() >= rel.quantityRequired()
                                        )
                                )
                )
                .collectList()

                .flatMap(list ->
                        Flux.fromIterable(list)
                                .flatMap(item ->
                                        materialRepo.findById(item.materialTypeId())
                                                .map(mat ->
                                                        new MaterialCheck(
                                                                item.materialTypeId(),
                                                                mat.name(),
                                                                item.required(),
                                                                item.available(),
                                                                item.exists(),
                                                                item.ok()
                                                        )
                                                )
                                )
                                .collectList()
                )

                .map(finalList ->
                        new StockSummary(
                                finalList.stream().allMatch(MaterialCheck::ok),
                                finalList
                        )
                );
    }
}