package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import com.redcomunitaria.backend.domain.model.Region;

/**
 * Puerto de entrada: Casos de uso de Regiones
 */
public interface RegionUseCase {
    
    Region create(Region region);
    
    Region update(Long id, Region region);
    
    void delete(Long id);
    
    List<Region> getAll();
    
    Region getById(Long id);
    
    List<String> getDepartamentos();
    
    List<Region> getMunicipiosByDepartamento(String departamento);
}
