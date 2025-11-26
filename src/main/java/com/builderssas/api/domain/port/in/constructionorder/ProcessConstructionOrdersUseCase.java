package com.builderssas.api.domain.port.in.constructionorder;

import reactor.core.publisher.Mono;

/**
 * Caso de uso para procesar órdenes:
 *  - Cron AM: pasar PENDING → IN_PROGRESS
 *  - Cron PM: pasar IN_PROGRESS → FINISHED
 */
public interface ProcessConstructionOrdersUseCase {

    Mono<Void> processMorningOrders();

    Mono<Void> processNightOrders();
}
