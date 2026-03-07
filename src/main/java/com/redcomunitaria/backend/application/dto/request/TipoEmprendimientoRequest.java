package com.redcomunitaria.backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Request para crear/actualizar tipo de emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEmprendimientoRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
}
