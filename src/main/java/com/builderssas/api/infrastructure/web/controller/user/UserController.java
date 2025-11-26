package com.builderssas.api.infrastructure.web.controller.user;

import com.builderssas.api.domain.port.in.user.CreateUserUseCase;
import com.builderssas.api.domain.port.in.user.GetAllUsersUseCase;
import com.builderssas.api.domain.port.in.user.GetUserByIdUseCase;
import com.builderssas.api.domain.port.in.user.UpdateUserUseCase;
import com.builderssas.api.domain.port.in.user.DeleteUserUseCase;
import com.builderssas.api.domain.port.in.user.GetInactiveUsersUseCase;
import com.builderssas.api.domain.port.in.user.GetActiveUsersUseCase; // 🔹 AGREGADO

import com.builderssas.api.infrastructure.web.dto.user.CreateUserDto;
import com.builderssas.api.infrastructure.web.dto.user.UpdateUserDto;
import com.builderssas.api.infrastructure.web.dto.user.DeleteUserDto;
import com.builderssas.api.infrastructure.web.dto.user.UserDto;
import com.builderssas.api.infrastructure.web.mapper.UserWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador WebFlux para la gestión de usuarios.
 *
 * Responsabilidades:
 * - Recibir y validar peticiones HTTP.
 * - Delegar en los casos de uso correspondientes.
 * - Convertir modelos de dominio (UserRecord) a DTOs (UserDto) mediante UserWebMapper.
 *
 * No contiene lógica de negocio ni acceso a infraestructura.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetInactiveUsersUseCase getInactiveUsersUseCase;
    private final GetActiveUsersUseCase getActiveUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /**
     * Obtiene todos los usuarios (activos + inactivos).
     *
     * Se usa principalmente en vistas administrativas
     * donde se requiere el listado completo.
     *
     * @return flujo reactivo de UserDto
     */
    @GetMapping
    public Flux<UserDto> getAll() {
        return getAllUsersUseCase.getAll()
                .map(UserWebMapper::toDto);
    }

    /**
     * Obtiene únicamente los usuarios activos del sistema.
     *
     * Esta ruta permite discriminar explícitamente los registros habilitados,
     * facilitando vistas operativas o validaciones administrativas.
     *
     * @return flujo reactivo de UserDto
     */
    @GetMapping("/active")
    public Flux<UserDto> getAllActive() {
        return getActiveUsersUseCase.getActiveUsers()
                .map(UserWebMapper::toDto);
    }

    /**
     * Obtiene todos los usuarios inactivos (soft delete).
     *
     * Útil para procesos de auditoría,
     * validaciones de offboarding o revisiones administrativas.
     *
     * @return flujo reactivo de UserDto
     */
    @GetMapping("/inactive")
    public Flux<UserDto> getInactive() {
        return getInactiveUsersUseCase.getInactiveUsers()
                .map(UserWebMapper::toDto);
    }

    /**
     * Obtiene un usuario por su identificador único.
     *
     * @param id identificador del usuario
     * @return Mono con el usuario encontrado como UserDto
     */
    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable Long id) {
        return getUserByIdUseCase.getById(id)
                .map(UserWebMapper::toDto);
    }

    /**
     * Crea un nuevo usuario.
     *
     * Reglas:
     * - El ID se genera en base de datos.
     * - El usuario se crea siempre activo.
     * - Las validaciones se aplican en CreateUserDto y en el caso de uso.
     *
     * @param dto datos de creación
     * @return Mono con el usuario creado como UserDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> create(@Valid @RequestBody CreateUserDto dto) {
        return createUserUseCase.createUser(dto)
                .map(UserWebMapper::toDto);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * Reglas:
     * - El ID viene en el cuerpo del DTO (no en la URL).
     * - Se valida que el usuario exista.
     * - Se valida la unicidad del correo electrónico.
     *
     * @param dto datos actualizados del usuario
     * @return Mono con el usuario actualizado como UserDto
     */
    @PutMapping
    public Mono<UserDto> update(@Valid @RequestBody UpdateUserDto dto) {
        return updateUserUseCase.updateUser(dto)
                .map(UserWebMapper::toDto);
    }

    /**
     * Realiza un borrado lógico (soft delete) de un usuario.
     *
     * Reglas:
     * - El ID viene en el cuerpo del DTO.
     * - No se elimina físicamente el registro; solo se marca como inactivo.
     *
     * @param dto contiene el identificador del usuario a desactivar
     * @return Mono<Void> indicando finalización de la operación
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@Valid @RequestBody DeleteUserDto dto) {
        return deleteUserUseCase.deleteUser(dto);
    }
}
