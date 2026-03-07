package com.redcomunitaria.backend.application.usecase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.dto.response.DashboardResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaRegionResponse;
import com.redcomunitaria.backend.application.dto.response.EstadisticaTipoResponse;
import com.redcomunitaria.backend.application.dto.response.TopDepartamentoResponse;
import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;
import com.redcomunitaria.backend.domain.port.in.EstadisticaUseCase;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.EmprendimientoJpaRepository;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstadisticaUseCaseImpl implements EstadisticaUseCase {
    
    private final EmprendimientoJpaRepository emprendimientoRepository;
    private final UsuarioJpaRepository usuarioRepository;

    @Override
    public List<EstadisticaTipoResponse> obtenerEstadisticasPorTipo() {
        List<Object[]> resultados = emprendimientoRepository.findEstadisticasPorTipo();
        
        return resultados.stream()
            .map(row -> EstadisticaTipoResponse.builder()
                .tipoId(((Number) row[0]).longValue())
                .tipoNombre((String) row[1])
                .cantidad(((Number) row[2]).longValue())
                .inversionTotal(convertToBigDecimal(row[3]))
                .inversionPromedio(convertToBigDecimal(row[4]))
                .empleadosTotal(((Number) row[5]).intValue())
                .empleadosPromedio(((Number) row[6]).doubleValue())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public List<EstadisticaRegionResponse> obtenerEstadisticasPorRegion() {
        List<Object[]> resultados = emprendimientoRepository.findEstadisticasPorRegion();
        
        return resultados.stream()
            .map(row -> EstadisticaRegionResponse.builder()
                .regionId(((Number) row[0]).longValue())
                .departamento((String) row[1])
                .municipio((String) row[2])
                .cantidad(((Number) row[3]).longValue())
                .inversionTotal(convertToBigDecimal(row[4]))
                .inversionPromedio(convertToBigDecimal(row[5]))
                .empleadosTotal(((Number) row[6]).intValue())
                .empleadosPromedio(((Number) row[7]).doubleValue())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public List<TopDepartamentoResponse> obtenerTopDepartamentos(int limit) {
        List<Object[]> resultados = emprendimientoRepository.findTopDepartamentos(limit);
        
        return resultados.stream()
            .map(row -> TopDepartamentoResponse.builder()
                .departamento((String) row[0])
                .cantidadEmprendimientos(((Number) row[1]).longValue())
                .empleadosTotal(((Number) row[2]).intValue())
                .inversionTotal(convertToBigDecimal(row[3]))
                .build())
            .collect(Collectors.toList());
    }
    
    /**
     * Convierte un objeto a BigDecimal manejando Double y BigDecimal
     */
    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        return BigDecimal.ZERO;
    }

    @Override
    public DashboardResponse obtenerDashboard() {
        // Totales generales
        long totalEmprendimientos = emprendimientoRepository.count();
        long totalUsuarios = usuarioRepository.count();
        
        // Suma de empleados e inversión total
        List<Object[]> totales = emprendimientoRepository.findEstadisticasPorTipo();
        int totalEmpleados = totales.stream()
            .mapToInt(row -> ((Number) row[5]).intValue())
            .sum();
        BigDecimal inversionTotal = totales.stream()
            .map(row -> convertToBigDecimal(row[3]))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Top 5 tipos
        List<EstadisticaTipoResponse> topTipos = obtenerEstadisticasPorTipo().stream()
            .limit(5)
            .collect(Collectors.toList());
        
        // Top 5 departamentos
        List<TopDepartamentoResponse> topDepartamentos = obtenerTopDepartamentos(5);
        
        // Distribución por estado
        List<Object[]> estadosData = emprendimientoRepository.countByEstado();
        Map<String, Long> distribucionEstados = new HashMap<>();
        estadosData.forEach(row -> {
            EstadoEmprendimiento estado = (EstadoEmprendimiento) row[0];
            Long cantidad = ((Number) row[1]).longValue();
            distribucionEstados.put(estado.name(), cantidad);
        });
        
        return DashboardResponse.builder()
            .totalEmprendimientos(totalEmprendimientos)
            .totalUsuarios(totalUsuarios)
            .totalEmpleados(totalEmpleados)
            .inversionTotal(inversionTotal)
            .topTipos(topTipos)
            .topDepartamentos(topDepartamentos)
            .distribucionEstados(distribucionEstados)
            .build();
    }
}
