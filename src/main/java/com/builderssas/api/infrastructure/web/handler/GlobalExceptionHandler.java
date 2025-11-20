package com.builderssas.api.infrastructure.web.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manejador global de excepciones para la capa web (WebFlux).
 *
 * Responsabilidades:
 * - Convertir excepciones técnicas y de dominio en respuestas HTTP claras.
 * - Unificar el formato de respuesta de error.
 * - NO contiene lógica de negocio.
 *
 * Arquitectura:
 * - Capa: infraestructura → web → handler.
 * - Aplica sobre todos los controladores REST.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================
    // MODELO DE RESPUESTA
    // ==========================

    private Map<String, Object> body(HttpStatus status, String message) {
        return Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "timestamp", LocalDateTime.now().toString(),
                "message", Optional.ofNullable(message)
                        .filter(m -> !m.isBlank())
                        .orElse("Error interno del servidor")
        );
    }

    private Map<String, Object> body(HttpStatus status, List<String> messages) {
        return body(status, String.join(" | ", messages));
    }

    // ==========================
    // 404 – NOT FOUND
    // ==========================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    // ==========================
    // 409 – CONFLICT (DUPLICADOS / INTEGRIDAD)
    // ==========================

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
                .body(body(status, "Violación de integridad de datos."));
    }

    // ==========================
    // 400 – BAD REQUEST (VALIDACIÓN / DATOS INVÁLIDOS)
    // ==========================

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationException ex) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(body(status, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(body(status, errors));
    }

    // ==========================
    // 401 – UNAUTHORIZED
    // ==========================

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    // ==========================
    // 403 – FORBIDDEN
    // ==========================

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    // ==========================
    // 422 – BUSINESS / REGLA DE NEGOCIO
    // ==========================

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY; // 422
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    // ==========================
    // 500 – ERROR INTERNO
    // ==========================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(body(status, ex.getMessage()));
    }

    // =====================================================
    // EXCEPCIONES DE DOMINIO / APLICACIÓN (SIMPLES)
    // =====================================================

    /**
     * 404 – Recurso no encontrado.
     */
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) { super(message); }
    }

    /**
     * 409 – Recurso duplicado o ya existente.
     */
    public static class DuplicateResourceException extends RuntimeException {
        public DuplicateResourceException(String message) { super(message); }
    }

    /**
     * 401 – Usuario no autenticado.
     */
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) { super(message); }
    }

    /**
     * 403 – Usuario autenticado pero sin permisos.
     */
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) { super(message); }
    }

    /**
     * 400 – Datos inválidos o petición mal formada.
     */
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) { super(message); }
    }

    /**
     * 422 – Regla de negocio incumplida.
     */
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) { super(message); }
    }
}
