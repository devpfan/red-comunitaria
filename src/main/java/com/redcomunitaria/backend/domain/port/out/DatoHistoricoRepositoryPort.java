package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.DatoHistorico;

/**
 * Puerto de salida: Repositorio de datos históricos
 */
public interface DatoHistoricoRepositoryPort {
    
    DatoHistorico save(DatoHistorico datoHistorico);
    
    Optional<DatoHistorico> findById(Long id);
    
    List<DatoHistorico> findByEmprendimientoId(Long emprendimientoId);
    
    Optional<DatoHistorico> findByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio);
    
    void deleteById(Long id);
    
    boolean existsByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio);
}
