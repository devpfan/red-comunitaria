package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.RegionMapper;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.port.out.RegionRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.RegionJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador del repositorio de Regiones
 */
@Component
@RequiredArgsConstructor
public class RegionRepositoryAdapter implements RegionRepositoryPort {
    
    private final RegionJpaRepository jpaRepository;
    private final RegionMapper mapper;
    
    @Override
    public List<Region> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<Region> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Region save(Region region) {
        var entity = mapper.toEntity(region);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
