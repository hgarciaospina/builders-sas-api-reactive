package com.builderssas.api.infrastructure.security.config;

import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;
import com.builderssas.api.infrastructure.security.jwt.JwtAuthenticationWebFilter;
import com.builderssas.api.infrastructure.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuración de seguridad reactiva para WebFlux con JWT stateless.
 *
 * Principios:
 * - Stateless: sin sesiones, sin cookies, sin CSRF.
 * - Funcional: usa DSL de lambdas, sin configuración imperativa.
 * - Desacoplada: el filtro JWT es un WebFilter independiente, no un AuthenticationManager.
 * - Idempotente: los endpoints públicos /api/v1/auth/** no requieren autenticación previa.
 *
 * Flujo para endpoints públicos:
 * 1. authorizeExchange marca /api/v1/auth/** como permitAll.
 * 2. JwtAuthenticationWebFilter se ejecuta pero hace bypass interno para /login y /refresh.
 * 3. Si no hay token válido, la petición continúa sin Authentication.
 * 4. Si el endpoint requiere auth, fallará después con 401 vía CustomAuthenticationEntryPoint.
 *
 * @since 3.0.0
 */
@Configuration
public class SecurityConfig {

    private final JwtTokenValidatorPort jwtTokenValidatorPort;

    /**
     * Inyecta el puerto de dominio para validar JWT sin acoplar a infraestructura.
     *
     * @param jwtTokenValidatorPort adaptador que valida firma, expiración y revocación
     */
    public SecurityConfig(JwtTokenValidatorPort jwtTokenValidatorPort) {
        this.jwtTokenValidatorPort = jwtTokenValidatorPort;
    }

    /**
     * Registra el filtro JWT como bean para inyectarlo en la cadena de seguridad.
     *
     * @return instancia de JwtAuthenticationWebFilter con el validador inyectado
     */
    @Bean
    public JwtAuthenticationWebFilter jwtAuthenticationWebFilter() {
        return new JwtAuthenticationWebFilter(jwtTokenValidatorPort);
    }

    /**
     * Construye la cadena de filtros de seguridad para WebFlux.
     *
     * Configuración aplicada:
     * 1. Deshabilita httpBasic, formLogin y logout de Spring: no usamos estado de sesión.
     * 2. Deshabilita CSRF: la API es stateless y usa tokens Bearer.
     * 3. Permite todo /api/v1/auth/**: login, refresh, logout, recover, create-credentials.
     * 4. Requiere autenticación para cualquier otro exchange.
     * 5. Inserta JwtAuthenticationWebFilter en la posición AUTHENTICATION.
     * 6. Usa CustomAuthenticationEntryPoint para devolver 401 JSON sin redirección.
     *
     * @param http bean de configuración provisto por Spring Security WebFlux
     * @param jwtAuthenticationWebFilter filtro JWT registrado como bean
     * @param unauthorizedEntryPoint manejador de 401 personalizado
     * @return cadena de filtros reactiva completamente configurada
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtAuthenticationWebFilter jwtAuthenticationWebFilter,
            CustomAuthenticationEntryPoint unauthorizedEntryPoint
    ) {
        return http
                // Deshabilitar mecanismos stateful y por defecto
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Reglas de autorización: todo auth es público, el resto requiere JWT
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        .anyExchange().authenticated()
                )

                // Filtro JWT antes de que Spring intente autenticar
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                // Manejo de errores: 401 JSON sin redirect
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                )

                .build();
    }
}