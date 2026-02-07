package com.redcomunitaria.backend.domain.port.out;

import java.util.Optional;

import com.redcomunitaria.backend.domain.model.PasswordResetToken;

/**
 * Puerto de salida: Repositorio de tokens de reseteo de contraseña
 */
public interface PasswordResetTokenRepositoryPort {
    
    /**
     * Guarda un token de reseteo
     */
    PasswordResetToken save(PasswordResetToken token);
    
    /**
     * Busca un token válido por su código
     */
    Optional<PasswordResetToken> findByCodeAndNotUsed(String code);
    
    /**
     * Invalida todos los tokens de un usuario
     */
    void invalidateUserTokens(Long usuarioId);
    
    /**
     * Elimina tokens expirados
     */
    void deleteExpiredTokens();
}
