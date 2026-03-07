package com.redcomunitaria.backend.application.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaTipoResponse {
    private Long tipoId;
    private String tipoNombre;
    private Long cantidad;
    private BigDecimal inversionTotal;
    private BigDecimal inversionPromedio;
    private Integer empleadosTotal;
    private Double empleadosPromedio;
}
