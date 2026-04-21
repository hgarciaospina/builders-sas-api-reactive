package com.builderssas.api.application.exception;

/**
 * Excepción de inconsistencias de datos en repositorios.
 *
 * Responsabilidad:
 * - Señalar que una operación de consulta devolvió más de un registro donde
 *   se esperaba uno único (resultado no único).
 * - Facilitar la propagación de la excepción hasta el GlobalExceptionHandler
 *   para devolver HTTP 422 (Unprocessable Entity) con mensaje estandarizado.
 *
 * Diseño:
 * - 100% compatible con programación funcional y reactiva (WebFlux, Mono/Flux).
 * - Hexagonal y alineada a DDD: no acopla lógica de presentación ni de persistencia.
 * - Inmutable y final.
 */
public final class DataInconsistencyException extends RuntimeException {

    /**
     * Crea una excepción con mensaje descriptivo de la inconsistencia.
     *
     * @param message Mensaje que describe la inconsistencia de datos detectada.
     */
    public DataInconsistencyException(String message) {
        super(message);
    }
}