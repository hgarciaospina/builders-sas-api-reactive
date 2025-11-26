package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener el listado de usuarios activos del sistema.
 */
public interface GetActiveUsersUseCase {

    /**
     * Recupera todos los usuarios con active = true.
     *
     * @return Flux con los usuarios activos.
     */
    Flux<UserRecord> getActiveUsers();
}
