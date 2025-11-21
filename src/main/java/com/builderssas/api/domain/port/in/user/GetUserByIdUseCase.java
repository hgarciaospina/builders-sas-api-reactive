package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener la información de un usuario por su identificador.
 */
public interface GetUserByIdUseCase {

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param id identificador del usuario.
     * @return Mono con el usuario encontrado o error si no existe.
     */
    Mono<UserRecord> getById(Long id);
}
