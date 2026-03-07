package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoFiltros;
import com.redcomunitaria.backend.domain.model.Emprendimiento;

/**
 * Puerto de entrada: Casos de uso de Emprendimientos
 */
public interface EmprendimientoUseCase {
    
    Emprendimiento create(Emprendimiento emprendimiento);
    
    Emprendimiento update(Long id, Emprendimiento emprendimiento);
    
    Emprendimiento getById(Long id);
    
    List<Emprendimiento> getAll();
    
    Page<Emprendimiento> getAll(Pageable pageable);
    
    Page<Emprendimiento> buscar(EmprendimientoFiltros filtros, Pageable pageable);
    
    void delete(Long id);
}
