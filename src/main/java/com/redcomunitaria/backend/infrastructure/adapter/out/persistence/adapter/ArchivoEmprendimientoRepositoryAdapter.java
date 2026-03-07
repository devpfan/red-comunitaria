package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.mapper.ArchivoEmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.domain.port.out.ArchivoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.ArchivoEmprendimientoJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador: Repositorio de archivos de emprendimiento
 */
@Component
@RequiredArgsConstructor
public class ArchivoEmprendimientoRepositoryAdapter implements ArchivoEmprendimientoRepositoryPort {
    
    private final ArchivoEmprendimientoJpaRepository jpaRepository;
    private final ArchivoEmprendimientoMapper mapper;
    
    @Override
    public ArchivoEmprendimiento save(ArchivoEmprendimiento archivo) {
        var entity = mapper.toEntity(archivo);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<ArchivoEmprendimiento> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<ArchivoEmprendimiento> findByEmprendimientoId(Long emprendimientoId) {
        return jpaRepository.findByEmprendimientoId(emprendimientoId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByNombreAlmacenado(String nombreAlmacenado) {
        return jpaRepository.existsByNombreAlmacenado(nombreAlmacenado);
    }
}
