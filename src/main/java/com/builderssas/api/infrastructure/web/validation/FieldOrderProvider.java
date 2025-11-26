package com.builderssas.api.infrastructure.web.validation;

import java.util.List;

/**
 * Contrato que permite a un DTO declarar el orden en el que
 * deben evaluarse y reportarse los errores de validación de sus campos.
 *
 * Los DTO que implementen esta interfaz podrán controlar:
 * - Qué campo se valida primero a nivel de mensaje.
 * - Qué mensaje de error se devuelve como prioridad.
 *
 * El GlobalExceptionHandler usará este orden para seleccionar
 * el primer mensaje de error que se enviará al cliente.
 */
public interface FieldOrderProvider {

    /**
     * Devuelve la lista de nombres de campos en el orden de prioridad
     * para los mensajes de validación.
     *
     * @return lista inmutable de nombres de campos, en orden de importancia.
     */
    List<String> fieldOrder();
}
