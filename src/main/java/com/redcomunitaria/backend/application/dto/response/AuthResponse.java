package com.redcomunitaria.backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Response de autenticación
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    private String type;
    private UsuarioResponse usuario;
    
    public AuthResponse(String token, UsuarioResponse usuario) {
        this.token = token;
        this.type = "Bearer";
        this.usuario = usuario;
    }
}
