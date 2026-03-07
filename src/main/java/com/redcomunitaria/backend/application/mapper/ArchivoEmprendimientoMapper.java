package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.ArchivoEmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.ArchivoEmprendimientoEntity;

/**
 * Mapper: Archivo de emprendimiento
 * Combina MapStruct (domain↔entity) y método estático (domain→response)
 * MapStruct mapea automáticamente el enum TipoArchivo ↔ TipoArchivoEntity
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArchivoEmprendimientoMapper {
    
    /**
     * Convierte de ArchivoEmprendimientoEntity a ArchivoEmprendimiento (domain)
     */
    ArchivoEmprendimiento toDomain(ArchivoEmprendimientoEntity entity);
    
    /**
     * Convierte de ArchivoEmprendimiento (domain) a ArchivoEmprendimientoEntity
     */
    ArchivoEmprendimientoEntity toEntity(ArchivoEmprendimiento archivo);
    
    /**
     * Convierte de ArchivoEmprendimiento (domain) a ArchivoEmprendimientoResponse (DTO)
     * Método estático para compatibilidad con código existente
     */
    static ArchivoEmprendimientoResponse toResponse(ArchivoEmprendimiento domain, String baseUrl) {
        return ArchivoEmprendimientoResponse.builder()
                .id(domain.getId())
                .emprendimientoId(domain.getEmprendimientoId())
                .nombreOriginal(domain.getNombreOriginal())
                .nombreAlmacenado(domain.getNombreAlmacenado())
                .tipoArchivo(domain.getTipoArchivo())
                .mimeType(domain.getMimeType())
                .tamanio(domain.getTamanio())
                .urlDescarga(baseUrl + "/api/emprendimientos/" + domain.getEmprendimientoId() + "/archivos/" + domain.getId())
                .descripcion(domain.getDescripcion())
                .build();
    }
}
