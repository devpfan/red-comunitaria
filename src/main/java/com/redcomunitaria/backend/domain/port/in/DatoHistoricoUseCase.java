package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.application.dto.request.DatoHistoricoRequest;
import com.redcomunitaria.backend.domain.model.DatoHistorico;

/**
 * Puerto de entrada: Casos de uso de datos históricos
 */
public interface DatoHistoricoUseCase {
    
    DatoHistorico create(Long emprendimientoId, DatoHistoricoRequest request, Long usuarioId);
    
    DatoHistorico update(Long emprendimientoId, Integer anio, DatoHistoricoRequest request, Long usuarioId);
    
    List<DatoHistorico> getByEmprendimiento(Long emprendimientoId);
    
    DatoHistorico getByEmprendimientoAndAnio(Long emprendimientoId, Integer anio);
    
    void delete(Long emprendimientoId, Integer anio, Long usuarioId);
}
