package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.domain.port.out.ArchivoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.ArchivoEmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.ArchivoEmprendimientoJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador: Repositorio de archivos de emprendimiento
 */
@Component
@RequiredArgsConstructor
public class ArchivoEmprendimientoRepositoryAdapter implements ArchivoEmprendimientoRepositoryPort {
    
    private final ArchivoEmprendimientoJpaRepository jpaRepository;
    
    @Override
    public ArchivoEmprendimiento save(ArchivoEmprendimiento archivo) {
        ArchivoEmprendimientoEntity entity = toEntity(archivo);
        ArchivoEmprendimientoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<ArchivoEmprendimiento> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public List<ArchivoEmprendimiento> findByEmprendimientoId(Long emprendimientoId) {
        return jpaRepository.findByEmprendimientoId(emprendimientoId)
                .stream()
                .map(this::toDomain)
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
    
    private ArchivoEmprendimientoEntity toEntity(ArchivoEmprendimiento domain) {
        return ArchivoEmprendimientoEntity.builder()
                .id(domain.getId())
                .emprendimientoId(domain.getEmprendimientoId())
                .nombreOriginal(domain.getNombreOriginal())
                .nombreAlmacenado(domain.getNombreAlmacenado())
                .tipoArchivo(com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoArchivoEntity.valueOf(domain.getTipoArchivo().name()))
                .mimeType(domain.getMimeType())
                .tamanio(domain.getTamanio())
                .rutaArchivo(domain.getRutaArchivo())
                .descripcion(domain.getDescripcion())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
    
    private ArchivoEmprendimiento toDomain(ArchivoEmprendimientoEntity entity) {
        return ArchivoEmprendimiento.builder()
                .id(entity.getId())
                .emprendimientoId(entity.getEmprendimientoId())
                .nombreOriginal(entity.getNombreOriginal())
                .nombreAlmacenado(entity.getNombreAlmacenado())
                .tipoArchivo(com.redcomunitaria.backend.domain.model.TipoArchivo.valueOf(entity.getTipoArchivo().name()))
                .mimeType(entity.getMimeType())
                .tamanio(entity.getTamanio())
                .rutaArchivo(entity.getRutaArchivo())
                .descripcion(entity.getDescripcion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
