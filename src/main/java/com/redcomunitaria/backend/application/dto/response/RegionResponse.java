package com.redcomunitaria.backend.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Respuesta de Región
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {
    
    private Long id;
    private String departamento;
    private String municipio;
    private String corregimiento;
    private Long poblacion;
}
