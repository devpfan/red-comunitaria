package com.redcomunitaria.backend.application.usecase;

import java.util.List;

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
    public List<TipoEmprendimiento> getAll() {
        return tipoRepository.findAll();
    }
    
    @Override
    public TipoEmprendimiento getById(Long id) {
        return tipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de emprendimiento no encontrado con ID: " + id));
    }
}
