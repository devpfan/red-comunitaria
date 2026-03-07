package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.DatoHistoricoResponse;
import com.redcomunitaria.backend.domain.model.DatoHistorico;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.DatoHistoricoEntity;

/**
 * Mapper: Dato Histórico
 * Combina MapStruct (domain↔entity) y método estático (domain→response)
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DatoHistoricoMapper {
    
    /**
     * Convierte de DatoHistoricoEntity a DatoHistorico (domain)
     */
    DatoHistorico toDomain(DatoHistoricoEntity entity);
    
    /**
     * Convierte de DatoHistorico (domain) a DatoHistoricoEntity
     */
    DatoHistoricoEntity toEntity(DatoHistorico datoHistorico);
    
    /**
     * Convierte de DatoHistorico (domain) a DatoHistoricoResponse (DTO)
     * Método estático para compatibilidad con código existente
     */
    static DatoHistoricoResponse toResponse(DatoHistorico domain) {
        return DatoHistoricoResponse.builder()
                .id(domain.getId())
                .emprendimientoId(domain.getEmprendimientoId())
                .anio(domain.getAnio())
                .ingresos(domain.getIngresos())
                .empleos(domain.getEmpleos())
                .innovaciones(domain.getInnovaciones())
                .fechaRegistro(domain.getFechaRegistro())
                .build();
    }
}
