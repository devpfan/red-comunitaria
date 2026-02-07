package com.redcomunitaria.backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Respuesta de Tipo de Emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEmprendimientoResponse {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
}
