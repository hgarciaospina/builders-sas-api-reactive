package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.UserEntity;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Adaptador R2DBC para la entidad {@link UserEntity}.
 *
 * Este componente pertenece a la capa de infraestructura dentro de la
 * Arquitectura Hexagonal. Su responsabilidad es estrictamente técnica:
 *
 *   • Convertir entre Entity ↔ Record del dominio
 *   • Invocar al repositorio reactivo R2DBC
 *
 * No contiene lógica de negocio.
 */
@Component
@RequiredArgsConstructor
public class UserR2dbcAdapter implements UserRepositoryPort {

    /** Repositorio R2DBC encargado del acceso a la base de datos. */
    private final UserR2dbcRepository repository;

    // ============================================================================
    // MAPPERS — Funcionales, sin imperativa, estandarizados
    // ============================================================================

    /**
     * Convierte una {@link UserEntity} en su representación del dominio
     * {@link UserRecord}.
     *
     * @param e entidad persistida
     * @return instancia del dominio o null
     */
    private UserRecord toDomain(UserEntity e) {
        return Optional.ofNullable(e)
                .map(x -> new UserRecord(
                        x.getId(),
                        x.getUsername(),
                        x.getDisplayName(),
                        x.getEmail(),
                        x.getActive()
                ))
                .orElse(null);
    }

    /**
     * Convierte un {@link UserRecord} en una {@link UserEntity}
     * lista para persistencia.
     *
     * @param d record del dominio
     * @return entity equivalente o null
     */
    private UserEntity toEntity(UserRecord d) {
        return Optional.ofNullable(d)
                .map(x -> {
                    UserEntity e = new UserEntity();
                    e.setId(x.id());
                    e.setUsername(x.username());
                    e.setDisplayName(x.displayName());
                    e.setEmail(x.email());
                    e.setActive(x.active());
                    return e;
                })
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    /**
     * Busca un usuario por ID.
     *
     * @param id identificador único
     * @return Mono con el record del dominio
     */
    @Override
    public Mono<UserRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Flux con los registros
     */
    @Override
    public Flux<UserRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    /**
     * Guarda o actualiza un usuario.
     *
     * @param aggregate record del dominio
     * @return Mono con la versión persistida
     */
    @Override
    public Mono<UserRecord> save(UserRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
