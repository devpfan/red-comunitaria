package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EmprendimientoEntity;

/**
 * Repositorio JPA: Emprendimiento
 */
@Repository
public interface EmprendimientoJpaRepository extends JpaRepository<EmprendimientoEntity, Long> {
}
