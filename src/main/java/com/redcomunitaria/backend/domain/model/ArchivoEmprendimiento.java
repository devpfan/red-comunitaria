package com.redcomunitaria.backend.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio: Archivo de emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoEmprendimiento {
    
    private Long id;
    private Long emprendimientoId;
    private String nombreOriginal;
    private String nombreAlmacenado;
    private TipoArchivo tipoArchivo;
    private String mimeType;
    private Long tamanio;
    private String rutaArchivo;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
