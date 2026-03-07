package com.redcomunitaria.backend.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Filtros de búsqueda para emprendimientos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoFiltros {
    
    private String nombre;
    private Long sectorId;
    private Long tipoEmprendimientoId;
    private Long regionId;
    private String departamento;
    private String municipio;
    private EstadoEmprendimiento estado;
    private LocalDate fechaCreacionDesde;
    private LocalDate fechaCreacionHasta;
    private BigDecimal inversionMinima;
    private BigDecimal inversionMaxima;
    private Integer empleadosMinimo;
    private Integer empleadosMaximo;
}
