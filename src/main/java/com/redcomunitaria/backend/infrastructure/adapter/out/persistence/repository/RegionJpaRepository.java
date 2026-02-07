package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;

/**
 * Repositorio JPA: Región
 */
@Repository
public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {
}
