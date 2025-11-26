package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener el listado de usuarios desactivados.
 */
public interface GetInactiveUsersUseCase {

    /**
     * Recupera todos los usuarios con active = false.
     *
     * @return Flux con los usuarios desactivados.
     */
    Flux<UserRecord> getInactiveUsers();
}
