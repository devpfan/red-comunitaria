package com.redcomunitaria.backend.domain.exception;

/**
 * Excepción: Recurso duplicado
 */
public class DuplicateException extends RuntimeException {
    
    public DuplicateException(String message) {
        super(message);
    }
    
    public DuplicateException(String resource, String field, Object value) {
        super(String.format("%s ya existe con %s: %s", resource, field, value));
    }
}
