package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.SectorResponse;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.SectorEntity;

/**
 * Mapper entre Sector (domain) y SectorEntity (JPA)
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectorMapper {
    
    Sector toDomain(SectorEntity entity);
    
    SectorEntity toEntity(Sector sector);
    
    SectorResponse toResponse(Sector sector);
}
