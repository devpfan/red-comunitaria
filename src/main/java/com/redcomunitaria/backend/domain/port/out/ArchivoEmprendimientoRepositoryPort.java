package com.redcomunitaria.backend.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;

/**
 * Puerto de salida: Repositorio de archivos de emprendimiento
 */
public interface ArchivoEmprendimientoRepositoryPort {
    
    ArchivoEmprendimiento save(ArchivoEmprendimiento archivo);
    
    Optional<ArchivoEmprendimiento> findById(Long id);
    
    List<ArchivoEmprendimiento> findByEmprendimientoId(Long emprendimientoId);
    
    void deleteById(Long id);
    
    boolean existsByNombreAlmacenado(String nombreAlmacenado);
}
