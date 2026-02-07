package com.redcomunitaria.backend.application.usecase;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.dto.request.ChangePasswordRequest;
import com.redcomunitaria.backend.application.dto.request.ForgotPasswordRequest;
import com.redcomunitaria.backend.application.dto.request.LoginRequest;
import com.redcomunitaria.backend.application.dto.request.RegisterRequest;
import com.redcomunitaria.backend.application.dto.request.ResetPasswordRequest;
import com.redcomunitaria.backend.application.dto.response.AuthResponse;
import com.redcomunitaria.backend.application.dto.response.MessageResponse;
import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.domain.exception.AuthenticationException;
import com.redcomunitaria.backend.domain.exception.DuplicateException;
import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.PasswordResetToken;
import com.redcomunitaria.backend.domain.model.Rol;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.AuthUseCase;
import com.redcomunitaria.backend.domain.port.out.PasswordResetTokenRepositoryPort;
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
    private final PasswordResetTokenRepositoryPort tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    
    private static final int TOKEN_EXPIRATION_HOURS = 24;
    private static final int CODE_LENGTH = 6;
    private final Random random = new Random();
    
    /**
     * Genera un código numérico de 6 dígitos
     */
    private String generateResetCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    
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
    
    @Override
    @Transactional
    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario", "email", request.getEmail()));
        
        // Invalidar tokens anteriores del usuario
        tokenRepository.invalidateUserTokens(usuario.getId());
        
        // Generar código de 6 dígitos
        String code = generateResetCode();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS);
        
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .code(code)
                .usuario(usuario)
                .expiryDate(expiryDate)
                .used(false)
                .createdAt(LocalDateTime.now())
                .build();
        
        tokenRepository.save(resetToken);
        
        // TODO: Enviar email con el código (implementar servicio de email)
        // Por ahora, retornar mensaje con el código (solo para desarrollo)
        return new MessageResponse(
            "Se ha enviado un código de verificación a tu correo. Código: " + code
        );
    }
    
    @Override
    @Transactional
    public MessageResponse resetPassword(ResetPasswordRequest request) {
        // Buscar código válido (no usado)
        PasswordResetToken resetToken = tokenRepository.findByCodeAndNotUsed(request.getCode())
                .orElseThrow(() -> new NotFoundException("Código inválido o ya usado"));
        
        // Validar que no esté expirado
        if (resetToken.isExpired()) {
            throw new AuthenticationException("El código ha expirado. Solicita uno nuevo.");
        }
        
        // Cargar usuario completo desde BD (el token solo tiene datos parciales)
        Usuario usuario = usuarioRepository.findById(resetToken.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("Usuario", "id", resetToken.getUsuario().getId().toString()));
        
        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepository.save(usuario);
        
        // Marcar token como usado
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        
        return new MessageResponse("Contraseña actualizada exitosamente");
    }
    
    @Override
    @Transactional
    public MessageResponse changePassword(ChangePasswordRequest request) {
        // Obtener usuario actual
        UsuarioResponse currentUser = getCurrentUser();
        Usuario usuario = usuarioRepository.findById(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Usuario", "id", currentUser.getId().toString()));
        
        // Verificar contraseña actual
        if (!passwordEncoder.matches(request.getCurrentPassword(), usuario.getPassword())) {
            throw new AuthenticationException("La contraseña actual es incorrecta");
        }
        
        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepository.save(usuario);
        
        return new MessageResponse("Contraseña cambiada exitosamente");
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
