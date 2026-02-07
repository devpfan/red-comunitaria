package com.redcomunitaria.backend.domain.port.in;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.domain.model.TipoArchivo;

/**
 * Puerto de entrada: Casos de uso de archivos de emprendimiento
 */
public interface ArchivoEmprendimientoUseCase {
    
    ArchivoEmprendimiento uploadFile(Long emprendimientoId, MultipartFile file, TipoArchivo tipoArchivo, String descripcion, Long usuarioId);
    
    List<ArchivoEmprendimiento> getFilesByEmprendimiento(Long emprendimientoId);
    
    Resource downloadFile(Long emprendimientoId, Long archivoId, Long usuarioId);
    
    void deleteFile(Long emprendimientoId, Long archivoId, Long usuarioId);
}
