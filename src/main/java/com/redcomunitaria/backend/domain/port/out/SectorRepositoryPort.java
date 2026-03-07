package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.Sector;

/**
 * Puerto de salida: Repositorio de Sectores
 */
public interface SectorRepositoryPort {
    
    List<Sector> findAll();
    
    List<Sector> findByActivoTrue();
    
    Optional<Sector> findById(Long id);
    
    Sector save(Sector sector);
    
    void deleteById(Long id);
}
