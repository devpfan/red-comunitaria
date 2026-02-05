package com.redcomunitaria.backend.domain.port.in;

import com.redcomunitaria.backend.application.dto.request.LoginRequest;
import com.redcomunitaria.backend.application.dto.request.RegisterRequest;
import com.redcomunitaria.backend.application.dto.response.AuthResponse;
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
}
