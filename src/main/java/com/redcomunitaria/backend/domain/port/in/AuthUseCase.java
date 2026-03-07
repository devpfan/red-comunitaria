package com.redcomunitaria.backend.domain.port.in;

import com.redcomunitaria.backend.application.dto.request.ChangePasswordRequest;
import com.redcomunitaria.backend.application.dto.request.ForgotPasswordRequest;
import com.redcomunitaria.backend.application.dto.request.LoginRequest;
import com.redcomunitaria.backend.application.dto.request.RegisterRequest;
import com.redcomunitaria.backend.application.dto.request.ResetPasswordRequest;
import com.redcomunitaria.backend.application.dto.request.UpdateProfileRequest;
import com.redcomunitaria.backend.application.dto.response.AuthResponse;
import com.redcomunitaria.backend.application.dto.response.MessageResponse;
import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;

/**
 * Puerto de entrada: Casos de uso de autenticación
 */
public interface AuthUseCase {
    
    /**
     * Registra un nuevo usuario
     */
    AuthResponse register(RegisterRequest request);
    
    /**
     * Autentica un usuario
     */
    AuthResponse login(LoginRequest request);
    
    /**
     * Obtiene el usuario actual
     */
    UsuarioResponse getCurrentUser();
    
    /**
     * Actualiza el perfil del usuario actual
     */
    UsuarioResponse updateProfile(UpdateProfileRequest request);
    
    /**
     * Solicita el reseteo de contraseña (envía token)
     */
    MessageResponse forgotPassword(ForgotPasswordRequest request);
    
    /**
     * Resetea la contraseña usando un token
     */
    MessageResponse resetPassword(ResetPasswordRequest request);
    
    /**
     * Cambia la contraseña del usuario autenticado
     */
    MessageResponse changePassword(ChangePasswordRequest request);
}
