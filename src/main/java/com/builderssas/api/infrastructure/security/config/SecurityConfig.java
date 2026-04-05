package com.builderssas.api.infrastructure.security.config;

import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;
import com.builderssas.api.infrastructure.security.jwt.JwtAuthenticationWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;

/**
 * Configuración de seguridad reactiva usando WebFlux y JWT.
 * Implementación funcional y respetando las clases existentes.
 */
@Configuration
public class SecurityConfig {

    private final JwtTokenValidatorPort jwtTokenValidatorPort;

    /**
     * Inyección del port de validación JWT del dominio.
     *
     * @param jwtTokenValidatorPort port de validación JWT
     */
    public SecurityConfig(JwtTokenValidatorPort jwtTokenValidatorPort) {
        this.jwtTokenValidatorPort = jwtTokenValidatorPort;
    }

    /**
     * Registro del filtro JWT existente.
     *
     * @return JwtAuthenticationWebFilter
     */
    @Bean
    public JwtAuthenticationWebFilter jwtAuthenticationWebFilter() {
        return new JwtAuthenticationWebFilter(jwtTokenValidatorPort);
    }

    /**
     * EntryPoint reactivo para respuestas 401.
     *
     * @return HttpStatusServerEntryPoint
     */
    @Bean
    public HttpStatusServerEntryPoint unauthorizedEntryPoint() {
        return new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Configura la cadena de filtros de seguridad WebFlux.
     * - Deshabilita mecanismos por defecto (httpBasic, formLogin, CSRF)
     * - Permite endpoints públicos para autenticación
     * - Aplica filtro JWT existente
     * - Maneja excepciones de autenticación de forma funcional
     *
     * @param http configuración de ServerHttpSecurity
     * @param jwtAuthenticationWebFilter filtro de autenticación JWT existente
     * @param unauthorizedEntryPoint entry point para 401
     * @return SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtAuthenticationWebFilter jwtAuthenticationWebFilter,
            HttpStatusServerEntryPoint unauthorizedEntryPoint
    ) {
        return http
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        .anyExchange().authenticated()
                )

                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                )

                .build();
    }
}