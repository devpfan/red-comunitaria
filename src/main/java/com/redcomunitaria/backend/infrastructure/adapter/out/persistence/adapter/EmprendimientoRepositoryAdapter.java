package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.EmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.port.out.EmprendimientoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.EmprendimientoJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador del repositorio de Emprendimientos
 */
@Component
@RequiredArgsConstructor
public class EmprendimientoRepositoryAdapter implements EmprendimientoRepositoryPort {
    
    private final EmprendimientoJpaRepository jpaRepository;
    private final EmprendimientoMapper mapper;
    
    @Override
    public Emprendimiento save(Emprendimiento emprendimiento) {
        var entity = mapper.toEntity(emprendimiento);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Emprendimiento> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Emprendimiento> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
