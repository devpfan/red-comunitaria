package com.redcomunitaria.backend.application.usecase;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.dto.request.LoginRequest;
import com.redcomunitaria.backend.application.dto.request.RegisterRequest;
import com.redcomunitaria.backend.application.dto.response.AuthResponse;
import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.domain.exception.AuthenticationException;
import com.redcomunitaria.backend.domain.exception.DuplicateException;
import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Rol;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.AuthUseCase;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;
import com.redcomunitaria.backend.infrastructure.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * Implementación de casos de uso de autenticación
 */
@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {
    
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Usuario", "email", request.getEmail());
        }
        
        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .rol(Rol.USER)
                .build();
        
        // Guardar usuario
        Usuario savedUsuario = usuarioRepository.save(usuario);
        
        // Generar token
        String token = tokenProvider.generateTokenFromUsername(savedUsuario.getEmail());
        
        // Preparar response
        UsuarioResponse usuarioResponse = mapToUsuarioResponse(savedUsuario);
        
        return new AuthResponse(token, usuarioResponse);
    }
    
    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            // Autenticar
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generar token
            String token = tokenProvider.generateToken(authentication);
            
            // Obtener usuario
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new NotFoundException("Usuario", "email", request.getEmail()));
            
            UsuarioResponse usuarioResponse = mapToUsuarioResponse(usuario);
            
            return new AuthResponse(token, usuarioResponse);
            
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException("Credenciales inválidas");
        }
    }
    
    @Override
    public UsuarioResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("No hay usuario autenticado");
        }
        
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario", "email", email));
        
        return mapToUsuarioResponse(usuario);
    }
    
    private UsuarioResponse mapToUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .fechaRegistro(usuario.getFechaRegistro())
                .activo(usuario.getActivo())
                .rol(usuario.getRol())
                .build();
    }
}
