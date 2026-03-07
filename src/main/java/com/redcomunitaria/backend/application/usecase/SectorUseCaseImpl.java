package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.port.in.SectorUseCase;
import com.redcomunitaria.backend.domain.port.out.SectorRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación: Casos de uso de Sectores
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectorUseCaseImpl implements SectorUseCase {
    
    private final SectorRepositoryPort sectorRepository;
    
    @Override
    @Cacheable("sectores")
    public List<Sector> getAll() {
        return sectorRepository.findAll();
    }
    
    @Override
    @Cacheable("sectores-activos")
    public List<Sector> getAllActivos() {
        return sectorRepository.findByActivoTrue();
    }
    
    @Override
    @Cacheable(value = "sectores", key = "#id")
    public Sector getById(Long id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sector no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"sectores", "sectores-activos"}, allEntries = true)
    public Sector create(Sector sector) {
        return sectorRepository.save(sector);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"sectores", "sectores-activos"}, allEntries = true)
    public Sector update(Long id, Sector sector) {
        Sector existing = getById(id);
        existing.setCodigo(sector.getCodigo());
        existing.setNombre(sector.getNombre());
        existing.setDescripcion(sector.getDescripcion());
        existing.setActivo(sector.getActivo());
        return sectorRepository.save(existing);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = {"sectores", "sectores-activos"}, allEntries = true)
    public void delete(Long id) {
        Sector existing = getById(id);
        sectorRepository.deleteById(existing.getId());
    }
}
