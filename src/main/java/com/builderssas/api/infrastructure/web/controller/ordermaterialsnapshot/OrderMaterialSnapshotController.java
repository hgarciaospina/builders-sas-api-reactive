package com.builderssas.api.infrastructure.web.controller.ordermaterialsnapshot;

import com.builderssas.api.domain.port.in.ordermaterialsnapshot.GetAllOrderMaterialSnapshotsUseCase;
import com.builderssas.api.domain.port.in.ordermaterialsnapshot.GetOrderMaterialSnapshotsByOrderIdUseCase;
import com.builderssas.api.infrastructure.web.dto.ordermaterialsnapshot.OrderMaterialSnapshotDto;
import com.builderssas.api.infrastructure.web.mapper.OrderMaterialSnapshotWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Controlador WebFlux para exponer los snapshots de materiales.
 *
 * No permite creación manual: el sistema genera los snapshots automáticamente
 * cuando se crea una orden de construcción.
 */
@RestController
@RequestMapping("/api/order-material-snapshots")
@RequiredArgsConstructor
public class OrderMaterialSnapshotController {

    private final GetAllOrderMaterialSnapshotsUseCase getAllUseCase;
    private final GetOrderMaterialSnapshotsByOrderIdUseCase getByOrderIdUseCase;

    /**
     * Obtiene todos los snapshots registrados.
     *
     * @return Flux<OrderMaterialSnapshotDto>
     */
    @GetMapping
    public Flux<OrderMaterialSnapshotDto> getAll() {
        return getAllUseCase.getAll()
                .map(OrderMaterialSnapshotWebMapper::toDto);
    }

    /**
     * Obtiene los snapshots asociados a una orden específica.
     *
     * @param orderId ID de la orden.
     * @return Flux<OrderMaterialSnapshotDto>
     */
    @GetMapping("/order/{orderId}")
    public Flux<OrderMaterialSnapshotDto> getByOrderId(@PathVariable Long orderId) {
        return getByOrderIdUseCase.getByOrderId(orderId)
                .map(OrderMaterialSnapshotWebMapper::toDto);
    }
}
