package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.infrastructure.web.dto.user.DeleteUserDto;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para realizar un borrado lógico (soft delete) de usuarios.
 */
public interface DeleteUserUseCase {

    /**
     * Ejecuta el borrado lógico de un usuario a partir de su identificador.
     *
     * @param deleteUserDto datos necesarios para el borrado lógico.
     * @return Mono vacío que indica la finalización de la operación.
     */
    Mono<Void> deleteUser(DeleteUserDto deleteUserDto);
}
