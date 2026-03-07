package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.DatoHistoricoEntity;

/**
 * Repositorio JPA: Dato Histórico
 */
@Repository
public interface DatoHistoricoJpaRepository extends JpaRepository<DatoHistoricoEntity, Long> {
    
    List<DatoHistoricoEntity> findByEmprendimientoId(Long emprendimientoId);
    
    Optional<DatoHistoricoEntity> findByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio);
    
    boolean existsByEmprendimientoIdAndAnio(Long emprendimientoId, Integer anio);
}
