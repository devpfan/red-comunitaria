package com.redcomunitaria.backend.application.mapper;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.dto.response.RegionResponse;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;

/**
 * Mapper entre Región (domain) y RegionEntity (JPA)
 */
@Component
public class RegionMapper {
    
    public Region toDomain(RegionEntity entity) {
        if (entity == null) {
            return null;
        }
        return Region.builder()
                .id(entity.getId())
                .departamento(entity.getDepartamento())
                .municipio(entity.getMunicipio())
                .corregimiento(entity.getCorregimiento())
                .poblacion(entity.getPoblacion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public RegionEntity toEntity(Region region) {
        if (region == null) {
            return null;
        }
        return RegionEntity.builder()
                .id(region.getId())
                .departamento(region.getDepartamento())
                .municipio(region.getMunicipio())
                .corregimiento(region.getCorregimiento())
                .poblacion(region.getPoblacion())
                .createdAt(region.getCreatedAt())
                .updatedAt(region.getUpdatedAt())
                .build();
    }
    
    public RegionResponse toResponse(Region region) {
        if (region == null) {
            return null;
        }
        return RegionResponse.builder()
                .id(region.getId())
                .departamento(region.getDepartamento())
                .municipio(region.getMunicipio())
                .corregimiento(region.getCorregimiento())
                .poblacion(region.getPoblacion())
                .build();
    }
}
