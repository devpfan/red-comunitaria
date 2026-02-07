package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.Region;

/**
 * Puerto de entrada: Casos de uso de Regiones
 */
public interface RegionUseCase {
    
    List<Region> getAll();
    
    Region getById(Long id);
}
