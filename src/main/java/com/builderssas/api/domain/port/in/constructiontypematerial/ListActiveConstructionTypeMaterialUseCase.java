package com.builderssas.api.domain.port.in.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada responsable de listar únicamente los registros activos
 * de ConstructionTypeMaterial.
 *
 * Representa un caso de uso atómico dentro de la Arquitectura Hexagonal,
 * delegando la lógica a la capa de dominio e interactuando con el puerto OUT.
 */
public interface ListActiveConstructionTypeMaterialUseCase {

    /**
     * Lista exclusivamente los registros con active = true.
     *
     * @return flujo reactivo con los registros activos
     */
    Flux<ConstructionTypeMaterialRecord> listActive();
}
