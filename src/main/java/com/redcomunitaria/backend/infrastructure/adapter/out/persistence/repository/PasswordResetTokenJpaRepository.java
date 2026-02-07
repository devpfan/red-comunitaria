package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.PasswordResetTokenEntity;

/**
 * Repositorio JPA: Token de reseteo de contraseña
 */
@Repository
public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    
    /**
     * Busca un token por su código
     */
    Optional<PasswordResetTokenEntity> findByCodeAndUsed(String code, boolean used);
    
    /**
     * Busca un token válido por email del usuario y código
     */
    @Query("SELECT t FROM PasswordResetTokenEntity t WHERE t.code = :code " +
           "AND t.used = false AND t.expiryDate > :now")
    Optional<PasswordResetTokenEntity> findValidByCode(String code, LocalDateTime now);
    
    /**
     * Elimina tokens expirados
     */
    @Modifying
    @Query("DELETE FROM PasswordResetTokenEntity t WHERE t.expiryDate < :now")
    void deleteExpiredTokens(LocalDateTime now);
    
    /**
     * Invalida todos los tokens de un usuario
     */
    @Modifying
    @Query("UPDATE PasswordResetTokenEntity t SET t.used = true WHERE t.usuario.id = :usuarioId AND t.used = false")
    void invalidateUserTokens(Long usuarioId);
}
