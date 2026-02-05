package com.redcomunitaria.backend.domain.exception;

/**
 * Excepción: Error de autenticación
 */
public class AuthenticationException extends RuntimeException {
    
    public AuthenticationException(String message) {
        super(message);
    }
}
