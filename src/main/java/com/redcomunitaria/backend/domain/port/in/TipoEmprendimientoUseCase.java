package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;

/**
 * Puerto de entrada: Casos de uso de Tipos de Emprendimiento
 */
public interface TipoEmprendimientoUseCase {
    
    List<TipoEmprendimiento> getAll();
    
    TipoEmprendimiento getById(Long id);
}
