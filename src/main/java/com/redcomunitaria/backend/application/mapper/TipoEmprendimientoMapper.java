package com.redcomunitaria.backend.application.mapper;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.dto.response.TipoEmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;

/**
 * Mapper entre TipoEmprendimiento (domain) y TipoEmprendimientoEntity (JPA)
 */
@Component
public class TipoEmprendimientoMapper {
    
    public TipoEmprendimiento toDomain(TipoEmprendimientoEntity entity) {
        if (entity == null) {
            return null;
        }
        return TipoEmprendimiento.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .categoria(entity.getCategoria())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public TipoEmprendimientoEntity toEntity(TipoEmprendimiento tipo) {
        if (tipo == null) {
            return null;
        }
        return TipoEmprendimientoEntity.builder()
                .id(tipo.getId())
                .nombre(tipo.getNombre())
                .descripcion(tipo.getDescripcion())
                .categoria(tipo.getCategoria())
                .createdAt(tipo.getCreatedAt())
                .updatedAt(tipo.getUpdatedAt())
                .build();
    }
    
    public TipoEmprendimientoResponse toResponse(TipoEmprendimiento tipo) {
        if (tipo == null) {
            return null;
        }
        return TipoEmprendimientoResponse.builder()
                .id(tipo.getId())
                .nombre(tipo.getNombre())
                .descripcion(tipo.getDescripcion())
                .categoria(tipo.getCategoria())
                .build();
    }
}
