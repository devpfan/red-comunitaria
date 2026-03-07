package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.RegionResponse;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;

/**
 * Mapper entre Región (domain) y RegionEntity (JPA)
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegionMapper {
    
    Region toDomain(RegionEntity entity);
    
    RegionEntity toEntity(Region region);
    
    RegionResponse toResponse(Region region);
}
