package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.infrastructure.web.dto.user.CreateUserDto;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para la creación de usuarios.
 *
 * Este puerto define la operación para registrar un nuevo usuario
 * aplicando reglas de negocio como la validación de unicidad del correo.
 *
 * Reglas:
 * - No conoce infraestructura ni repositorios.
 * - Recibe el DTO validado desde la capa Web.
 * - Retorna un UserRecord inmutable.
 */
public interface CreateUserUseCase {

    /**
     * Crea un nuevo usuario a partir de los datos proporcionados por la capa Web.
     *
     * @param createUserDto datos validados para la creación del usuario.
     * @return Mono con el usuario creado en forma de UserRecord.
     */
    Mono<UserRecord> createUser(CreateUserDto createUserDto);
}
