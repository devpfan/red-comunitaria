package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;

/**
 * Repositorio JPA: Tipo de Emprendimiento
 */
@Repository
public interface TipoEmprendimientoJpaRepository extends JpaRepository<TipoEmprendimientoEntity, Long> {
}
