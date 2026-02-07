package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.Emprendimiento;

/**
 * Puerto de salida: Repositorio de Emprendimientos
 */
public interface EmprendimientoRepositoryPort {
    
    Emprendimiento save(Emprendimiento emprendimiento);
    
    Optional<Emprendimiento> findById(Long id);
    
    List<Emprendimiento> findAll();
    
    void deleteById(Long id);
}
