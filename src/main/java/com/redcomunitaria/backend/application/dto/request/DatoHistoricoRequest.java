package com.redcomunitaria.backend.application.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Request: Crear/Actualizar dato histórico
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatoHistoricoRequest {
    
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2100, message = "El año debe ser menor a 2100")
    private Integer anio;
    
    @PositiveOrZero(message = "Los ingresos deben ser positivos o cero")
    private BigDecimal ingresos;
    
    @PositiveOrZero(message = "Los empleos deben ser positivos o cero")
    private Integer empleos;
    
    @PositiveOrZero(message = "Las innovaciones deben ser positivas o cero")
    private Integer innovaciones;
}
