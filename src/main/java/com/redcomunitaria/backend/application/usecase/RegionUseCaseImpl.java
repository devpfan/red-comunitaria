package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.port.in.RegionUseCase;
import com.redcomunitaria.backend.domain.port.out.RegionRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación: Casos de uso de Regiones
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionUseCaseImpl implements RegionUseCase {
    
    private final RegionRepositoryPort regionRepository;
    
    @Override
    @Cacheable("regiones")
    public List<Region> getAll() {
        return regionRepository.findAll();
    }
    
    @Override
    @Cacheable(value = "regiones", key = "#id")
    public Region getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Región no encontrada con ID: " + id));
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "regiones", allEntries = true)
    public Region create(Region region) {
        return regionRepository.save(region);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "regiones", allEntries = true)
    public Region update(Long id, Region region) {
        Region existing = getById(id);
        existing.setCodigoDivipola(region.getCodigoDivipola());
        existing.setDepartamento(region.getDepartamento());
        existing.setMunicipio(region.getMunicipio());
        existing.setCorregimiento(region.getCorregimiento());
        existing.setPoblacion(region.getPoblacion());
        return regionRepository.save(existing);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "regiones", allEntries = true)
    public void delete(Long id) {
        Region existing = getById(id);
        regionRepository.deleteById(existing.getId());
    }
}
