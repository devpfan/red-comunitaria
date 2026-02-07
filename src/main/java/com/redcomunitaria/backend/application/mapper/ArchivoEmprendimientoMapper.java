package com.redcomunitaria.backend.application.mapper;

import com.redcomunitaria.backend.application.dto.response.ArchivoEmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;

/**
 * Mapper: Archivo de emprendimiento
 */
public class ArchivoEmprendimientoMapper {
    
    public static ArchivoEmprendimientoResponse toResponse(ArchivoEmprendimiento domain, String baseUrl) {
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
