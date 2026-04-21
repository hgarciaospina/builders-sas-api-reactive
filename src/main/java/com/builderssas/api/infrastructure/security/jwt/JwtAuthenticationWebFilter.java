package com.builderssas.api.infrastructure.security.jwt;

import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Filtro WebFlux que autentica peticiones mediante JWT de forma 100% reactiva y funcional.
 *
 * Principios de diseño:
 * - Sin estructuras imperativas: no usa if, else, ni asignaciones mutables.
 * - Un solo punto de salida: chain.filter(exchange) se ejecuta exactamente una vez por petición.
 * - Idempotente: tolera header ausente, mal formado o token inválido sin lanzar excepciones.
 * - No bloqueante: toda la cadena usa operadores de Reactor.
 *
 * Comportamiento por caso:
 * - Path = /api/v1/auth/login, /api/v1/auth/refresh, /api/v1/auth/logout: Bypass, no valida token y continúa la cadena.
 * - Sin header Authorization: Continúa sin autenticación.
 * - Header sin prefijo "Bearer ": Continúa sin autenticación.
 * - Token inválido, expirado o revocado: Registra el error y continúa sin autenticación.
 * - Token válido: Inyecta Authentication en el contexto reactivo y continúa.
 *
 * Observabilidad: registra cada decisión en nivel DEBUG sin exponer el token completo.
 *
 * @since 3.0.0
 */
public final class JwtAuthenticationWebFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationWebFilter.class);

    /** Prefijo estándar para tokens Bearer según RFC 6750. */
    private static final String BEARER_PREFIX = "Bearer ";

    /** Rutas que se excluyen de validación para permitir flujo OAuth2 sin token de acceso. */
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/refresh",
            "/api/v1/auth/logout"
    );

    /** Puerto de dominio para validar JWT contra repositorio y reglas de negocio. */
    private final JwtTokenValidatorPort jwtTokenValidator;

    /**
     * Construye el filtro con el validador de tokens inyectado.
     *
     * @param jwtTokenValidator adaptador que valida firma, expiración y estado revocado
     */
    public JwtAuthenticationWebFilter(JwtTokenValidatorPort jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    /**
     * Intercepta cada petición HTTP y resuelve la autenticación de forma funcional.
     *
     * Flujo reactivo:
     * 1. Descarta la validación si el path está en EXCLUDED_PATHS.
     * 2. Extrae el header Authorization si existe.
     * 3. Valida prefijo "Bearer " y extrae el token con trim().
     * 4. Delega validación al JwtTokenValidatorPort.
     * 5. Si es válido, construye UsernamePasswordAuthenticationToken y lo inyecta.
     * 6. En cualquier fallo o ausencia, continúa la cadena sin autenticación.
     *
     * @param exchange contexto de la petición y respuesta HTTP reactiva
     * @param chain cadena de filtros de Spring Security a continuar
     * @return Mono<Void> que completa cuando termina el procesamiento
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.just(exchange)
                // 1. Excluir paths públicos de validación. Si está en la lista, el Mono queda vacío
                //    y cae directo al switchIfEmpty final, evitando procesamiento de token.
                .filter(ex -> !EXCLUDED_PATHS.contains(ex.getRequest().getPath().value()))

                // 2. Extraer header Authorization de forma segura. Mono.empty() si no existe.
                .flatMap(ex -> Mono.justOrEmpty(ex.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)))

                // 3. Validar formato Bearer. Si no empieza con "Bearer ", el Mono queda vacío.
                .filter(header -> header.startsWith(BEARER_PREFIX))

                // 4. Extraer el token y limpiar espacios o saltos de línea que rompen el match en BD.
                .map(header -> header.substring(BEARER_PREFIX.length()).trim())

                // 5. Validar token contra el dominio: firma, expiración, revocado.
                .flatMap(jwtTokenValidator::validateToken)

                // 6. Construir objeto Authentication con roles del usuario validado.
                .map(user -> new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.roles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                ))

                // 7. Inyectar Authentication en el contexto reactivo y continuar la cadena.
                .flatMap(auth -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                        .doOnSuccess(v -> log.debug("[JWT] Contexto de seguridad establecido"))
                )

                // 8. Capturar cualquier error de validación: token expirado, firma inválida, revocado, etc.
                //    En lugar de propagar, registramos y continuamos sin autenticación.
                .onErrorResume(ex -> {
                    log.debug("[JWT] Falló validación de token: {}. Continuando sin autenticación", ex.getMessage());
                    return chain.filter(exchange);
                })

                // 9. Punto único de salida para casos sin credenciales: path excluido, sin header, o header mal formado.
                //    Garantiza que chain.filter(exchange) se invoque exactamente una vez por petición.
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("[JWT] Sin credenciales válidas o bypass de path excluido. Continuando cadena");
                    return chain.filter(exchange);
                }));
    }
}