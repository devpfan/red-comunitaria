package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.Region;

/**
 * Puerto de salida: Repositorio de Regiones
 */
public interface RegionRepositoryPort {
    
    List<Region> findAll();
    
    Optional<Region> findById(Long id);
}
