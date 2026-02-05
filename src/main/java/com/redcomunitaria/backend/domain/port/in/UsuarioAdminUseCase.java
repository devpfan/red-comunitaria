package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.Rol;
import com.redcomunitaria.backend.domain.model.Usuario;

/**
 * Puerto de entrada: Casos de uso para administración de usuarios
 */
public interface UsuarioAdminUseCase {
    
    /**
     * Listar todos los usuarios
     */
    List<Usuario> getAllUsuarios();
    
    /**
     * Obtener un usuario por ID
     */
    Usuario getUsuarioById(Long id);
    
    /**
     * Activar un usuario
     */
    Usuario activarUsuario(Long id);
    
    /**
     * Desactivar un usuario
     */
    Usuario desactivarUsuario(Long id);
    
    /**
     * Cambiar el rol de un usuario
     */
    Usuario cambiarRol(Long id, Rol nuevoRol);
}
