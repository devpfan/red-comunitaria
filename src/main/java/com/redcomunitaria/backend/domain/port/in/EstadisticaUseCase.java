package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.application.dto.response.DashboardResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaRegionResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaTipoResponse;
import com.redcomunitaria.backend.application.dto.response.TopDepartamentoResponse;

public interface EstadisticaUseCase {
    
    /**
     * Obtiene estadísticas de producción agrupadas por tipo de emprendimiento
     */
    List<EstadisticaTipoResponse> obtenerEstadisticasPorTipo();
    
    /**
     * Obtiene estadísticas de producción agrupadas por región
     */
    List<EstadisticaRegionResponse> obtenerEstadisticasPorRegion();
    
    /**
     * Obtiene los top N departamentos con más emprendimientos
     * @param limit número de departamentos a retornar
     */
    List<TopDepartamentoResponse> obtenerTopDepartamentos(int limit);
    
    /**
     * Obtiene el dashboard general con todas las métricas principales
     */
    DashboardResponse obtenerDashboard();
}
