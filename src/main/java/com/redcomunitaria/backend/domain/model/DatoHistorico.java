package com.redcomunitaria.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio: Dato Histórico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatoHistorico {
    
    private Long id;
    private Long emprendimientoId;
    private Integer anio;
    private BigDecimal ingresos;
    private Integer empleos;
    private Integer innovaciones;
    private LocalDateTime fechaRegistro;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
