package com.redcomunitaria.backend.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Response: Dato histórico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatoHistoricoResponse {
    
    private Long id;
    private Long emprendimientoId;
    private Integer anio;
    private BigDecimal ingresos;
    private Integer empleos;
    private Integer innovaciones;
    private LocalDateTime fechaRegistro;
}
