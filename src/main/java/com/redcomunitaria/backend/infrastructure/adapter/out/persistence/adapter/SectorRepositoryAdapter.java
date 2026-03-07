package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.SectorMapper;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.port.out.SectorRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.SectorJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador del repositorio de Sectores
 */
@Component
@RequiredArgsConstructor
public class SectorRepositoryAdapter implements SectorRepositoryPort {
    
    private final SectorJpaRepository jpaRepository;
    private final SectorMapper mapper;
    
    @Override
    public List<Sector> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Sector> findByActivoTrue() {
        return jpaRepository.findByActivoTrue().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<Sector> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Sector save(Sector sector) {
        var entity = mapper.toEntity(sector);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
