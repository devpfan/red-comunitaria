package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.SectorEntity;

/**
 * Repositorio JPA: Sector
 */
@Repository
public interface SectorJpaRepository extends JpaRepository<SectorEntity, Long> {
    
    /**
     * Buscar sectores activos
     */
    List<SectorEntity> findByActivoTrue();
    
    /**
     * Buscar por código
     */
    SectorEntity findByCodigo(String codigo);
}
