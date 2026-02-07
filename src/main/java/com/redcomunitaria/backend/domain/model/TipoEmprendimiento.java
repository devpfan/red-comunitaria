package com.redcomunitaria.backend.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio: Tipo de Emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEmprendimiento {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
