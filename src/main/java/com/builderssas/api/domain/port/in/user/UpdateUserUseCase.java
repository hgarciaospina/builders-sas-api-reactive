package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.infrastructure.web.dto.user.UpdateUserDto;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para la actualización de usuarios.
 *
 * Aplica las reglas de negocio necesarias (existencia del usuario, unicidad
 * de correo electrónico, etc.) antes de persistir los cambios.
 */
public interface UpdateUserUseCase {

    /**
     * Actualiza la información de un usuario existente.
     *
     * El identificador del usuario se recibe en el cuerpo del DTO, siguiendo
     * las reglas del proyecto para operaciones de actualización.
     *
     * @param updateUserDto datos actualizados del usuario.
     * @return Mono con el usuario actualizado en el dominio.
     */
    Mono<UserRecord> updateUser(UpdateUserDto updateUserDto);
}
