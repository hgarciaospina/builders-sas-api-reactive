package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener el listado de usuarios del sistema.
 */
public interface GetAllUsersUseCase {

    /**
     * Recupera todos los usuarios registrados.
     *
     * @return Flux con los usuarios del dominio.
     */
    Flux<UserRecord> getAll();
}
