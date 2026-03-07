package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.TipoEmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.port.out.TipoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.TipoEmprendimientoJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador del repositorio de Tipos de Emprendimiento
 */
@Component
@RequiredArgsConstructor
public class TipoEmprendimientoRepositoryAdapter implements TipoEmprendimientoRepositoryPort {
    
    private final TipoEmprendimientoJpaRepository jpaRepository;
    private final TipoEmprendimientoMapper mapper;
    
    @Override
    public List<TipoEmprendimiento> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public Optional<TipoEmprendimiento> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public TipoEmprendimiento save(TipoEmprendimiento tipo) {
        var entity = mapper.toEntity(tipo);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
