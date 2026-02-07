package com.redcomunitaria.backend.application.dto.response;

import com.redcomunitaria.backend.domain.model.TipoArchivo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Response: Archivo de emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoEmprendimientoResponse {
    
    private Long id;
    private Long emprendimientoId;
    private String nombreOriginal;
    private String nombreAlmacenado;
    private TipoArchivo tipoArchivo;
    private String mimeType;
    private Long tamanio;
    private String urlDescarga;
    private String descripcion;
}
