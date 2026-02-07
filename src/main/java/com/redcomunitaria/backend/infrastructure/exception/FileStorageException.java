package com.redcomunitaria.backend.infrastructure.exception;

/**
 * Excepción: Error en almacenamiento de archivos
 */
public class FileStorageException extends RuntimeException {
    
    public FileStorageException(String message) {
        super(message);
    }
    
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
