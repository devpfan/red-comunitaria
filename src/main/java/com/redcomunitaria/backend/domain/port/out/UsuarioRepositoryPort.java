package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.Usuario;

/**
 * Puerto de salida: Repositorio de Usuario
 * Define las operaciones de persistencia para Usuario
 */
public interface UsuarioRepositoryPort {
    
    /**
     * Guarda un usuario
     */
    Usuario save(Usuario usuario);
    
    /**
     * Busca un usuario por ID
     */
    Optional<Usuario> findById(Long id);
    
    /**
     * Busca un usuario por email
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    Boolean existsByEmail(String email);
    
    /**
     * Obtiene todos los usuarios
     */
    List<Usuario> findAll();
}
