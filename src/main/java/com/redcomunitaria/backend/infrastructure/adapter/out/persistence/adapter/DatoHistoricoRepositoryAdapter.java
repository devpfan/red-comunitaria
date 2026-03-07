package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.DatoHistoricoMapper;
import com.redcomunitaria.backend.domain.model.DatoHistorico;
import com.redcomunitaria.backend.domain.port.out.DatoHistoricoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.DatoHistoricoJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador: Repositorio de datos históricos
 */
@Component
@RequiredArgsConstructor
public class DatoHistoricoRepositoryAdapter implements DatoHistoricoRepositoryPort {
    
    private final DatoHistoricoJpaRepository jpaRepository;
    private final DatoHistoricoMapper mapper;
    
    @Override
    public DatoHistorico save(DatoHistorico datoHistorico) {
        var entity = mapper.toEntity(datoHistorico);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<DatoHistorico> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<DatoHistorico> findByEmprendimientoId(Long emprendimientoId) {
        return jpaRepository.findByEmprendimientoId(emprendimientoId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<DatoHistorico> findByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio) {
        return jpaRepository.findByEmprendimientoIdAndAnio(emprendimientoId, anio)
                .map(mapper::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio) {
        return jpaRepository.existsByEmprendimientoIdAndAnio(emprendimientoId, anio);
    }
}
