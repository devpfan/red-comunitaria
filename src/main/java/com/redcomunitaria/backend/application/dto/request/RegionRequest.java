package com.redcomunitaria.backend.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Request para crear/actualizar región
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequest {
    
    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    private String departamento;
    
    @NotBlank(message = "El municipio es obligatorio")
    @Size(max = 100, message = "El municipio no puede exceder 100 caracteres")
    private String municipio;
    
    @Size(max = 100, message = "El corregimiento no puede exceder 100 caracteres")
    private String corregimiento;
    
    @Min(value = 0, message = "La población no puede ser negativa")
    private Long poblacion;
}
