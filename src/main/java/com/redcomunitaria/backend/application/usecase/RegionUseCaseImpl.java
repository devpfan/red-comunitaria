package com.redcomunitaria.backend.application.usecase;

import java.util.List;

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
    public List<Region> getAll() {
        return regionRepository.findAll();
    }
    
    @Override
    public Region getById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Región no encontrada con ID: " + id));
    }
}
