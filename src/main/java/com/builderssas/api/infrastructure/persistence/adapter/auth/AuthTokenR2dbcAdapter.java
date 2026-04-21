package com.builderssas.api.infrastructure.persistence.adapter.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.AuthTokenMapper;
import com.builderssas.api.infrastructure.persistence.repository.AuthTokenR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Adapter R2DBC que implementa el puerto AuthTokenRepositoryPort.
 *
 * Forma parte de la capa de infraestructura en arquitectura hexagonal.
 *
 * Responsabilidades:
 * - Persistir tokens de autenticación en base de datos.
 * - Recuperar tokens por su valor.
 * - Consultar estado de validez de tokens.
 *
 * Notas de diseño:
 * - Flujo 100% reactivo (Reactor + R2DBC).
 * - No contiene lógica de negocio.
 * - El mapeo dominio ↔ entidad se delega a AuthTokenMapper.
 * - No se realizan transformaciones adicionales fuera del mapeo.
 */
@Component
@RequiredArgsConstructor
public class AuthTokenR2dbcAdapter implements AuthTokenRepositoryPort {

    private final AuthTokenR2dbcRepository repository;

    private static final Logger log =
            LoggerFactory.getLogger(AuthTokenR2dbcAdapter.class);

    /**
     * Persiste un token en base de datos.
     *
     * @param token registro de dominio a guardar
     * @return token persistido en formato de dominio
     */
    @Override
    public Mono<AuthTokenRecord> save(AuthTokenRecord token) {
        return Mono.just(token)
                .map(AuthTokenMapper::toEntity)
                .flatMap(repository::save)
                .map(AuthTokenMapper::toDomain);
    }

    /**
     * Busca un token por su valor.
     *
     * @param token JWT en texto plano
     * @return token en formato de dominio si existe
     */
    @Override
    public Mono<AuthTokenRecord> findByToken(String token) {
        log.debug("BUSCANDO TOKEN RAW: [{}]", token);

        return Mono.justOrEmpty(token)
                .map(String::trim)
                .flatMap(repository::findByToken)
                .map(AuthTokenMapper::toDomain);
    }

    /**
     * Verifica si un token está activo.
     *
     * @param token JWT en texto plano
     * @param now   fecha actual de validación
     * @return true si el token es válido, false en caso contrario
     */
    @Override
    public Mono<Boolean> isTokenActive(String token, LocalDateTime now) {
        return repository.isTokenActive(token, now)
                .defaultIfEmpty(false);
    }
}