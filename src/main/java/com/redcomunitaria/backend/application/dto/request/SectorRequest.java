package com.redcomunitaria.backend.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Request para crear/actualizar sector económico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorRequest {
    
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede exceder 10 caracteres")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "El código debe contener solo letras mayúsculas y números")
    private String codigo;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;
    
    private String descripcion;
    
    private Boolean activo;
}
