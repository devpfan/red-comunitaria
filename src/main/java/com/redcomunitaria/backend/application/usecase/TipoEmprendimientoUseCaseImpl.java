package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.port.in.TipoEmprendimientoUseCase;
import com.redcomunitaria.backend.domain.port.out.TipoEmprendimientoRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación: Casos de uso de Tipos de Emprendimiento
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TipoEmprendimientoUseCaseImpl implements TipoEmprendimientoUseCase {
    
    private final TipoEmprendimientoRepositoryPort tipoRepository;
    
    @Override
    @Cacheable("tipos-emprendimiento")
    public List<TipoEmprendimiento> getAll() {
        return tipoRepository.findAll();
    }
    
    @Override
    @Cacheable(value = "tipos-emprendimiento", key = "#id")
    public TipoEmprendimiento getById(Long id) {
        return tipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de emprendimiento no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "tipos-emprendimiento", allEntries = true)
    public TipoEmprendimiento create(TipoEmprendimiento tipo) {
        return tipoRepository.save(tipo);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "tipos-emprendimiento", allEntries = true)
    public TipoEmprendimiento update(Long id, TipoEmprendimiento tipo) {
        TipoEmprendimiento existing = getById(id);
        existing.setNombre(tipo.getNombre());
        existing.setDescripcion(tipo.getDescripcion());
        return tipoRepository.save(existing);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "tipos-emprendimiento", allEntries = true)
    public void delete(Long id) {
        TipoEmprendimiento existing = getById(id);
        tipoRepository.deleteById(existing.getId());
    }
}
