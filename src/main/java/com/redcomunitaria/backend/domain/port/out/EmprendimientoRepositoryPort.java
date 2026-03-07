package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoFiltros;
import com.redcomunitaria.backend.domain.model.Emprendimiento;

/**
 * Puerto de salida: Repositorio de Emprendimientos
 */
public interface EmprendimientoRepositoryPort {
    
    Emprendimiento save(Emprendimiento emprendimiento);
    
    Optional<Emprendimiento> findById(Long id);
    
    List<Emprendimiento> findAll();
    
    Page<Emprendimiento> findAll(Pageable pageable);
    
    Page<Emprendimiento> findWithFiltros(EmprendimientoFiltros filtros, Pageable pageable);
    
    void deleteById(Long id);
}
