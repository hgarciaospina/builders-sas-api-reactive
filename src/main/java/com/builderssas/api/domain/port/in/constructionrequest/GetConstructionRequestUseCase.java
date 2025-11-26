package com.builderssas.api.domain.port.in.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para las operaciones de consulta relacionadas con
 * solicitudes de construcción dentro de la arquitectura hexagonal.
 *
 * Esta interfaz define los casos de uso disponibles para consulta y es
 * implementada por la capa de aplicación. No contiene lógica de negocio
 * ni dependencias hacia infraestructura; su función es describir el
 * contrato de consulta que los controladores u otras capas superiores
 * podrán invocar.
 *
 * Métodos expuestos:
 * - findById: consulta una solicitud específica según su ID.
 * - findAll: lista todas las solicitudes existentes sin filtros.
 *
 * Los métodos utilizan tipos reactivos (Mono/Flux) para integrarse
 * con el modelo no bloqueante de WebFlux.
 */
public interface GetConstructionRequestUseCase {

    /**
     * Recupera una solicitud de construcción según su identificador.
     *
     * @param id identificador único de la solicitud
     * @return Mono que contiene el record correspondiente o vacío si no existe
     */
    Mono<ConstructionRequestRecord> findById(Long id);

    /**
     * Recupera todas las solicitudes de construcción almacenadas.
     *
     * @return Flux con todos los records de solicitudes existentes
     */
    Flux<ConstructionRequestRecord> findAll();
}
