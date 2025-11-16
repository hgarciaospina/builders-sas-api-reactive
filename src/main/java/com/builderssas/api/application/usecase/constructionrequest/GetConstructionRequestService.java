package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Caso de uso encargado de consultar solicitudes de construcción.
 *
 * Esta clase pertenece a la capa de aplicación dentro de la arquitectura
 * hexagonal. Su responsabilidad consiste exclusivamente en coordinar el
 * acceso a los datos a través del puerto de salida correspondiente, sin
 * realizar lógica de negocio, validaciones o transformaciones adicionales.
 *
 * Métodos expuestos:
 * - findById: obtiene una solicitud específica.
 * - findAll: obtiene todas las solicitudes registradas.
 *
 * La implementación utiliza tipos reactivos (Mono/Flux) para integrarse
 * con el flujo no bloqueante de WebFlux.
 */
@Service
@RequiredArgsConstructor
public class GetConstructionRequestService implements GetConstructionRequestUseCase {

    /**
     * Puerto de salida que abstrae el acceso a persistencia para las
     * solicitudes de construcción. Su implementación concreta puede ser
     * un adaptador R2DBC u otro mecanismo de infraestructura.
     */
    private final ConstructionRequestRepositoryPort repository;

    /**
     * Recupera una solicitud específica según su identificador.
     *
     * No se aplica lógica de negocio en este nivel. La validación de
     * existencia o consistencia se delega al controlador o a capas
     * superiores según el contrato definido.
     *
     * @param id identificador de la solicitud
     * @return Mono con el record correspondiente o vacío si no existe
     */
    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Recupera todas las solicitudes de construcción almacenadas.
     *
     * No se realiza filtrado ni paginación en este caso de uso. Si se
     * requieren criterios adicionales, deberán implementarse en casos de
     * uso específicos siguiendo la misma estructura.
     *
     * @return Flux con todos los registros en forma de records del dominio
     */
    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll();
    }
}
