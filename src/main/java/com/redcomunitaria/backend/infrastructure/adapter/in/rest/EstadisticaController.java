package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.response.DashboardResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaRegionResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaTipoResponse;
import com.redcomunitaria.backend.application.dto.response.TopDepartamentoResponse;
import com.redcomunitaria.backend.domain.port.in.EstadisticaUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@Tag(name = "Estadísticas", description = "Endpoints para consultar estadísticas de emprendimientos")
@SecurityRequirement(name = "bearerAuth")
public class EstadisticaController {
    
    private final EstadisticaUseCase estadisticaUseCase;
    
    @GetMapping("/por-tipo")
    @Operation(summary = "Obtener estadísticas por tipo de emprendimiento",
               description = "Retorna métricas agrupadas por tipo: cantidad, inversión total/promedio, empleados total/promedio")
    public ResponseEntity<List<EstadisticaTipoResponse>> obtenerEstadisticasPorTipo() {
        return ResponseEntity.ok(estadisticaUseCase.obtenerEstadisticasPorTipo());
    }
    
    @GetMapping("/por-region")
    @Operation(summary = "Obtener estadísticas por región",
               description = "Retorna métricas agrupadas por región (departamento y municipio)")
    public ResponseEntity<List<EstadisticaRegionResponse>> obtenerEstadisticasPorRegion() {
        return ResponseEntity.ok(estadisticaUseCase.obtenerEstadisticasPorRegion());
    }
    
    @GetMapping("/top-departamentos")
    @Operation(summary = "Obtener top departamentos",
               description = "Retorna los N departamentos con más emprendimientos")
    public ResponseEntity<List<TopDepartamentoResponse>> obtenerTopDepartamentos(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(estadisticaUseCase.obtenerTopDepartamentos(limit));
    }
    
    @GetMapping("/dashboard")
    @Operation(summary = "Obtener dashboard general",
               description = "Retorna todas las métricas principales en un solo endpoint")
    public ResponseEntity<DashboardResponse> obtenerDashboard() {
        return ResponseEntity.ok(estadisticaUseCase.obtenerDashboard());
    }
}
