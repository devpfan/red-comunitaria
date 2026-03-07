package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.TipoEmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;

/**
 * Mapper entre TipoEmprendimiento (domain) y TipoEmprendimientoEntity (JPA)
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoEmprendimientoMapper {
    
    TipoEmprendimiento toDomain(TipoEmprendimientoEntity entity);
    
    TipoEmprendimientoEntity toEntity(TipoEmprendimiento tipo);
    
    TipoEmprendimientoResponse toResponse(TipoEmprendimiento tipo);
}
