package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.model.auth.RecoverPasswordRecord;
import com.builderssas.api.domain.port.in.auth.RecoverPasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso: Recuperación de contraseña.
 *
 * <p>Responsabilidad:
 * <ul>
 *   <li>Buscar usuario por username.</li>
 *   <li>Generar UNA SOLA contraseña temporal segura.</li>
 *   <li>Hashear y persistir esa contraseña en el registro AuthUser.</li>
 *   <li>Resolver roles activos del usuario.</li>
 *   <li>Retornar el {@link RecoverPasswordRecord} con la misma contraseña temporal
 *       que se guardó en base de datos.</li>
 * </ul>
 *
 * <p>BUG CORREGIDO (v1 → v2):
 * En la versión anterior, {@code generateTempPassword()} se invocaba dos veces:
 * una dentro de {@code updateWithTempPassword()} y otra en el {@code .map()} final.
 * Ambas llamadas producen UUIDs distintos, por lo que el cliente recibía una
 * contraseña diferente a la almacenada en BD, causando que {@code BCrypt.checkpw}
 * siempre fallara al intentar hacer login con la contraseña temporal.
 *
 * <p>Solución: la contraseña temporal se genera una única vez en {@code recover()}
 * y se pasa como parámetro a {@code updateWithTempPassword(auth, tempPassword)},
 * garantizando coherencia entre lo guardado y lo devuelto al cliente.
 *
 * <p>Todo el flujo es 100% reactivo, funcional y no bloqueante (WebFlux).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecoverPasswordService implements RecoverPasswordUseCase {

    private final UserR2dbcRepository userRepository;
    private final AuthUserRepositoryPort authRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;

    /**
     * Recupera la contraseña de un usuario generando una contraseña temporal.
     *
     * <p>Flujo funcional:
     * <ol>
     *   <li>Buscar usuario por username → 404 si no existe.</li>
     *   <li>Buscar credenciales del usuario → 404 si no existen.</li>
     *   <li>Generar UNA contraseña temporal (UUID corto).</li>
     *   <li>Hashear y persistir esa contraseña en BD.</li>
     *   <li>Resolver roles activos del usuario.</li>
     *   <li>Construir y retornar {@link RecoverPasswordRecord} con la misma contraseña temporal.</li>
     * </ol>
     *
     * @param username nombre de usuario cuya contraseña se va a recuperar
     * @return {@link Mono} con {@link RecoverPasswordRecord} que contiene la contraseña temporal
     * @throws IllegalStateException si el usuario o sus credenciales no existen
     */
    @Override
    public Mono<RecoverPasswordRecord> recover(String username) {

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException("Usuario no encontrado: " + username)
                ))
                .flatMap(user ->
                        authRepository.findByUserId(user.getId())
                                .switchIfEmpty(Mono.error(
                                        new IllegalStateException("Credenciales no encontradas para usuario: " + username)
                                ))
                                .flatMap(auth -> {

                                    /*
                                     * ✅ FIX: la contraseña temporal se genera UNA SOLA VEZ aquí
                                     * y se reutiliza tanto para persistir en BD como para devolver
                                     * al cliente. Antes se generaba dos veces, produciendo valores
                                     * distintos e inconsistentes.
                                     */
                                    String tempPassword = generateTempPassword();

                                    return updateWithTempPassword(auth, tempPassword)
                                            .then(resolveRoles(user.getId()))
                                            .map(roles -> new RecoverPasswordRecord(
                                                    user.getId(),
                                                    user.getUsername(),
                                                    user.getDisplayName(),
                                                    roles,
                                                    tempPassword // mismo valor guardado en BD
                                            ));
                                })
                );
    }

    // =========================================================================
    // MÉTODOS PRIVADOS
    // =========================================================================

    /**
     * Hashea la contraseña temporal recibida y persiste el {@link AuthUserRecord} actualizado.
     *
     * <p>Recibe el password en texto plano (ya generado externamente) para garantizar
     * que el valor hasheado en BD sea exactamente el que se entregará al cliente.
     *
     * <p>Observabilidad: cualquier error en el guardado se registra en el log antes
     * de propagarse, facilitando el diagnóstico en producción.
     *
     * @param auth        registro de autenticación original del usuario
     * @param tempPassword contraseña temporal en texto plano (generada en {@code recover()})
     * @return {@link Mono}{@code <Void>} que completa al terminar la persistencia
     */
    private Mono<Void> updateWithTempPassword(AuthUserRecord auth, String tempPassword) {
        String hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());

        AuthUserRecord updated = new AuthUserRecord(
                auth.id(),
                auth.userId(),
                hashed,
                auth.active(),
                auth.createdAt(),
                LocalDateTime.now() // updatedAt
        );

        return authRepository.save(updated)
                /*
                 * ✅ RECOMENDACIÓN: registrar errores de persistencia para facilitar
                 * el diagnóstico. Sin este log, un fallo silencioso en save() haría
                 * que el usuario reciba una contraseña temporal que nunca fue guardada.
                 */
                .doOnError(e -> log.error(
                        "[RecoverPassword] Error al guardar contraseña temporal para userId={}: {}",
                        auth.userId(), e.getMessage()
                ))
                .then();
    }

    /**
     * Resuelve todos los roles activos de un usuario como un String concatenado.
     *
     * <p>Si el usuario no tiene roles activos asignados, se asigna el rol por defecto
     * {@code ROLE_SUPPORT} para garantizar que el token JWT pueda construirse.
     *
     * @param userId identificador del usuario
     * @return {@link Mono}{@code <String>} con los roles separados por coma,
     *         o {@code "ROLE_SUPPORT"} si no hay roles activos
     */
    private Mono<String> resolveRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .filter(userRole -> userRole.isActive())
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(role -> role.getName())
                .collectList()
                .filter(list -> !list.isEmpty())
                .map(list -> String.join(",", list))
                .switchIfEmpty(Mono.just("ROLE_SUPPORT")); // rol por defecto si no hay ninguno activo
    }

    /**
     * Genera una contraseña temporal segura basada en UUID (8 caracteres).
     *
     * <p>Suficientemente aleatoria para uso temporal. Para mayor seguridad,
     * se puede reemplazar por {@code SecureRandom} con un alfabeto más amplio.
     *
     * @return String con los primeros 8 caracteres de un UUID aleatorio
     */
    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}