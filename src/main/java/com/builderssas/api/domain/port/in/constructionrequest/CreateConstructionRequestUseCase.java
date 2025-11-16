package com.builderssas.api.domain.port.in.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada responsable de la creación de solicitudes de construcción.
 *
 * Esta interfaz define el contrato que debe cumplir cualquier caso de uso
 * encargado de procesar la creación de una solicitud. No contiene lógica de
 * negocio ni dependencias hacia infraestructura; únicamente declara la
 * operación necesaria dentro de la arquitectura hexagonal.
 *
 * El método expuesto utiliza un tipo reactivo (Mono) para integrarse con
 * el paradigma no bloqueante de WebFlux.
 */
public interface CreateConstructionRequestUseCase {

    /**
     * Crea una nueva solicitud de construcción a partir del record
     * proporcionado por la capa superior (generalmente proveniente
     * del mapper web).
     *
     * @param command datos necesarios para registrar la solicitud
     * @return Mono con la solicitud creada en forma de record del dominio
     */
    Mono<ConstructionRequestRecord> create(ConstructionRequestRecord command);
}
