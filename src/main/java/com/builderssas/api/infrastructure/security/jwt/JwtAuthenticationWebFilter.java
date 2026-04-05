package com.builderssas.api.infrastructure.security.jwt;

import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * WebFilter reactivo para autenticación basada en JWT.
 *
 * <p>
 * Responsabilidades:
 * - Extraer token JWT del header Authorization
 * - Validar token de forma reactiva
 * - Construir Authentication
 * - Inyectar SecurityContext en Reactor Context
 *
 * <p>
 * Comportamiento:
 * - Si no hay token → continúa sin autenticación
 * - Si el token es inválido → continúa sin autenticación (NO rompe flujo)
 * - Si es válido → agrega Authentication al contexto
 */
public final class JwtAuthenticationWebFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenValidatorPort jwtTokenValidator;

    public JwtAuthenticationWebFilter(JwtTokenValidatorPort jwtTokenValidator) {
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return Mono.justOrEmpty(exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))

                // Validar formato Bearer
                .filter(header -> header.startsWith(BEARER_PREFIX))

                // Extraer token
                .map(header -> header.substring(BEARER_PREFIX.length()))

                // Validar token
                .flatMap(jwtTokenValidator::validateToken)

                // Construir Authentication
                .map(user -> new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.roles()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                ))

                // Inyectar contexto de seguridad
                .flatMap(authentication ->
                        chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                )

                /**
                 * 🔥 CLAVE:
                 * Si no hay token o falla validación → NO lanzar error
                 */
                .onErrorResume(ex -> chain.filter(exchange))

                /**
                 * 🔥 CLAVE:
                 * Si no hay Authorization header → continuar normal
                 */
                .switchIfEmpty(chain.filter(exchange));
    }
}