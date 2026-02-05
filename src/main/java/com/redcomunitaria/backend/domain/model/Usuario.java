package com.redcomunitaria.backend.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio: Usuario
 * Representa un usuario en el sistema
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
    private Rol rol;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Obtiene el nombre completo del usuario
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
