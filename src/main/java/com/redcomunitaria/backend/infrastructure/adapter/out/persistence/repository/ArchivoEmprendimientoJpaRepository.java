package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.ArchivoEmprendimientoEntity;

/**
 * Repositorio JPA: Archivo de emprendimiento
 */
@Repository
public interface ArchivoEmprendimientoJpaRepository extends JpaRepository<ArchivoEmprendimientoEntity, Long> {
    
    List<ArchivoEmprendimientoEntity> findByEmprendimientoId(Long emprendimientoId);
    
    boolean existsByNombreAlmacenado(String nombreAlmacenado);
}
