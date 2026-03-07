package com.redcomunitaria.backend.application.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalEmprendimientos;
    private Long totalUsuarios;
    private Integer totalEmpleados;
    private java.math.BigDecimal inversionTotal;
    
    // Top 5 por tipo
    private List<EstadisticaTipoResponse> topTipos;
    
    // Top 5 departamentos
    private List<TopDepartamentoResponse> topDepartamentos;
    
    // Distribución por estado
    private java.util.Map<String, Long> distribucionEstados;
}
