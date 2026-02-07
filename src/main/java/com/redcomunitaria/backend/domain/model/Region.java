package com.redcomunitaria.backend.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio: Región
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    
    private Long id;
    private String departamento;
    private String municipio;
    private String corregimiento;
    private Long poblacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
