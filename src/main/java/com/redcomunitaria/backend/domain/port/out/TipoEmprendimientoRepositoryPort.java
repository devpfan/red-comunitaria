package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;

/**
 * Puerto de salida: Repositorio de Tipos de Emprendimiento
 */
public interface TipoEmprendimientoRepositoryPort {
    
    List<TipoEmprendimiento> findAll();
    
    Optional<TipoEmprendimiento> findById(Long id);
}
