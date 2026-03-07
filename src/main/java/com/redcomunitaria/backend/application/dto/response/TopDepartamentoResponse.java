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
public class TopDepartamentoResponse {
    private String departamento;
    private Long cantidadEmprendimientos;
    private Integer empleadosTotal;
    private BigDecimal inversionTotal;
}
