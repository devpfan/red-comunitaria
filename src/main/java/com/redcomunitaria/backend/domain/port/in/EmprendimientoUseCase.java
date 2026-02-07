package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.Emprendimiento;

/**
 * Puerto de entrada: Casos de uso de Emprendimientos
 */
public interface EmprendimientoUseCase {
    
    Emprendimiento create(Emprendimiento emprendimiento);
    
    Emprendimiento update(Long id, Emprendimiento emprendimiento);
    
    Emprendimiento getById(Long id);
    
    List<Emprendimiento> getAll();
    
    void delete(Long id);
}
