package com.builderssas.api.infrastructure.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * EntryPoint reactivo para manejo de errores de autenticación.
 *
 * Responsabilidades:
 * - Interceptar errores de autenticación (401)
 * - Construir respuesta JSON consistente con GlobalExceptionHandler
 * - Mantener flujo 100% reactivo y no bloqueante
 *
 * Comportamiento:
 * - Si ocurre un error de autenticación → responde con estructura JSON estándar
 * - No utiliza lógica imperativa fuera del flujo reactivo
 * - No rompe el flujo reactivo
 */
@Component
public final class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Maneja errores de autenticación devolviendo respuesta estructurada.
     *
     * @param exchange contexto de la petición
     * @param ex excepción de autenticación
     * @return Mono<Void> completando la respuesta HTTP
     */
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        return Mono.justOrEmpty(ex.getMessage())
                .filter(message -> !message.isBlank())
                .switchIfEmpty(Mono.just("No autorizado"))

                .map(message -> Map.<String, Object>of(
                        "error", "Unauthorized",
                        "message", message,
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 401
                ))

                .flatMap(body -> Mono.fromCallable(() -> objectMapper.writeValueAsBytes(body)))

                .flatMap(bytes -> {
                    var response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
                })

                .onErrorResume(error -> exchange.getResponse().setComplete());
    }
}