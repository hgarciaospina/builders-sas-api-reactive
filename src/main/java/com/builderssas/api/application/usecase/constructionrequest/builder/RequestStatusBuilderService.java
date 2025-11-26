package com.builderssas.api.application.usecase.constructionrequest.builder;

import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Builds APPROVED or REJECTED ConstructionRequestRecord instances.
 *
 * Responsibilities:
 *  • Generate observation text for both outcomes.
 *  • Consolidate material requirement lines.
 *  • Centralize the formatting logic.
 *
 * Characteristics:
 *  • Pure transformation service (no IO, no DB).
 *  • Immutable domain record builder.
 *  • Keeps Orchestrator small and readable.
 */
@Service
@RequiredArgsConstructor
public class RequestStatusBuilderService {

    // =====================================================================
    //                           TEXT TEMPLATES
    // =====================================================================

    private static final String REQUEST_APPROVED_TEMPLATE = """
            Estado de solicitud: APROBADA

            Proyecto: %s
            Tipo de construcción: %s
            Coordenadas: (%s, %s)

            Validaciones:
            • Coordenadas libres
            • Stock suficiente

            Materiales requeridos:
            %s
            """;

    private static final String REQUEST_REJECTED_TEMPLATE = """
            Estado de solicitud: RECHAZADA

            Proyecto: %s
            Tipo de construcción: %s
            Coordenadas: (%s, %s)

            Validaciones:
            • Coordenadas: %s
            • Stock insuficiente

            Materiales con stock insuficiente:
            %s
            """;

    // =====================================================================
    //                      PUBLIC BUILD OPERATIONS
    // =====================================================================

    /**
     * Builds an APPROVED request record.
     */
    public ConstructionRequestRecord buildApproved(
            ConstructionRequestRecord original,
            ProjectRecord project,
            ConstructionTypeRecord type,
            StockValidationService.StockSummary stockSummary
    ) {

        String materials = stockSummary.items().stream()
                .map(i -> "- " + i.materialName() + ": " + i.required() + " unidades")
                .reduce("", (a, b) -> a + "\n" + b);

        String obs = REQUEST_APPROVED_TEMPLATE.formatted(
                project.name(),
                type.name(),
                original.latitude(),
                original.longitude(),
                materials
        );

        return new ConstructionRequestRecord(
                original.id(),
                original.projectId(),
                original.constructionTypeId(),
                original.latitude(),
                original.longitude(),
                original.requestedByUserId(),
                original.requestDate(),
                RequestStatus.APPROVED,
                obs,
                original.active()
        );
    }

    /**
     * Builds a REJECTED request record.
     */
    public ConstructionRequestRecord buildRejected(
            ConstructionRequestRecord original,
            ProjectRecord project,
            ConstructionTypeRecord type,
            boolean coordinatesOK,
            StockValidationService.StockSummary stockSummary
    ) {

        String coord = Optional.of(coordinatesOK)
                .filter(Boolean::booleanValue)
                .map(ok -> "LIBRES")
                .orElse("OCUPADAS");

        String insufficient = stockSummary.items().stream()
                .filter(i -> !i.ok())
                .map(i ->
                        "- " + i.materialName()
                                + " (Requerido: " + i.required()
                                + ", Disponible: " + i.available() + ")"
                )
                .reduce("", (a, b) -> a + "\n" + b);

        String obs = REQUEST_REJECTED_TEMPLATE.formatted(
                project.name(),
                type.name(),
                original.latitude(),
                original.longitude(),
                coord,
                insufficient
        );

        return new ConstructionRequestRecord(
                original.id(),
                original.projectId(),
                original.constructionTypeId(),
                original.latitude(),
                original.longitude(),
                original.requestedByUserId(),
                original.requestDate(),
                RequestStatus.REJECTED,
                obs,
                original.active()
        );
    }
}
