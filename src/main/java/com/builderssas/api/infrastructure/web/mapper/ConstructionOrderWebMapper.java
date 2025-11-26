package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import com.builderssas.api.infrastructure.web.dto.constructionorder.CreateConstructionOrderRequestDto;

/**
 * Mapper Web para convertir entre DTOs y Records.
 *
 * ✦ 100% estático
 * ✦ Sin @Component
 * ✦ Sin inyección en controladores
 * ✦ No contiene lógica de negocio
 * ✦ Solo transforma DTO ↔ Record
 *
 * Esta es la forma correcta dentro de una arquitectura
 * Hexagonal + WebFlux, permitiendo controladores ultra-livianos.
 */
public final class ConstructionOrderWebMapper {

    // Constructor privado para evitar instanciación
    private ConstructionOrderWebMapper() { }

    /**
     * Convierte un record de dominio a un DTO de respuesta HTTP.
     *
     * @param r record del dominio (inmutable)
     * @return DTO listo para ser enviado al cliente
     */
    public static ConstructionOrderResponseDto toResponse(ConstructionOrderRecord r) {
        return new ConstructionOrderResponseDto(
                r.id(),
                r.constructionRequestId(),
                r.projectId(),
                r.constructionTypeId(),
                r.requestedByUserId(),
                r.latitude(),
                r.longitude(),
                r.requestedDate(),
                r.scheduledStartDate(),
                r.scheduledEndDate(),
                r.orderStatus(),
                r.createdAt(),
                r.updatedAt(),
                r.observations(),
                r.active()
        );
    }

    /**
     * Convierte el DTO recibido en el POST a un record del dominio.
     *
     * Los campos id, createdAt y updatedAt van en null para que
     * el caso de uso asigne sus valores correctamente.
     *
     * @param dto DTO recibido desde el request body
     * @return record de dominio listo para procesar
     */
    public static ConstructionOrderRecord toDomain(CreateConstructionOrderRequestDto dto) {
        return new ConstructionOrderRecord(
                null,                           // id → generado por DB
                dto.constructionRequestId(),
                dto.projectId(),
                dto.constructionTypeId(),
                dto.requestedByUserId(),
                dto.latitude(),
                dto.longitude(),
                dto.requestedDate(),
                dto.scheduledStartDate(),
                dto.scheduledEndDate(),
                dto.orderStatus(),
                null,                           // createdAt → use case
                null,                           // updatedAt → use case
                dto.observations(),
                dto.active()
        );
    }
}
