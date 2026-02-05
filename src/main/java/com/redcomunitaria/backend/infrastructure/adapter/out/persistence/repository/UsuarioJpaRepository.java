package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

/**
 * Repositorio JPA para Usuario
 */
@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    
    /**
     * Busca un usuario por email
     */
    Optional<UsuarioEntity> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    Boolean existsByEmail(String email);
}
