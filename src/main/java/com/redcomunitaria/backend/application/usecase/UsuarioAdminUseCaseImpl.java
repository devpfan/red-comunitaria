package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Rol;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.UsuarioAdminUseCase;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación: Casos de uso para administración de usuarios
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioAdminUseCaseImpl implements UsuarioAdminUseCase {
    
    private final UsuarioRepositoryPort usuarioRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
    }
    
    @Override
    public Usuario activarUsuario(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario cambiarRol(Long id, Rol nuevoRol) {
        Usuario usuario = getUsuarioById(id);
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }
}
