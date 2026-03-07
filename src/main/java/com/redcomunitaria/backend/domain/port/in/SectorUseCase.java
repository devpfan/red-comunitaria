package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.Sector;

/**
 * Puerto de entrada: Casos de uso de Sectores
 */
public interface SectorUseCase {
    
    List<Sector> getAll();
    
    List<Sector> getAllActivos();
    
    Sector getById(Long id);
    
    Sector create(Sector sector);
    
    Sector update(Long id, Sector sector);
    
    void delete(Long id);
}
