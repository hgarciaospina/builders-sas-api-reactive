package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsable de crear solicitudes de construcción.
 *
 * Esta clase pertenece a la capa de aplicación dentro de la arquitectura
 * hexagonal. Su responsabilidad consiste en coordinar la operación de
 * creación delegando completamente la persistencia al puerto de salida
 * correspondiente, sin incorporar reglas de negocio ni validaciones
 * específicas dentro de esta capa.
 *
 * El registro recibido debe haber sido construido previamente mediante
 * el mapper correspondiente en la capa web. El servicio únicamente
 * invoca el repositorio para persistirlo y retorna la solicitud creada.
 *
 * Se utiliza un tipo reactivo (Mono) para integrarse con WebFlux.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionRequestService implements CreateConstructionRequestUseCase {

    /**
     * Puerto de salida que abstrae la persistencia de solicitudes de
     * construcción. Su implementación concreta se encuentra en la
     * infraestructura mediante un adaptador R2DBC.
     */
    private final ConstructionRequestRepositoryPort repository;

    /**
     * Persiste una nueva solicitud de construcción.
     *
     * Este método no contiene lógica adicional ni validación de reglas
     * de negocio. El record debe llegar completamente estructurado desde
     * las capas superiores (controlador + mapper).
     *
     * @param command record con los datos de la solicitud a crear
     * @return Mono con la solicitud persistida
     */
    @Override
    public Mono<ConstructionRequestRecord> create(ConstructionRequestRecord command) {
        return repository.save(command);
    }
}
