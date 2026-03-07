package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoFiltros;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EstadoEmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.SectorEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

/**
 * Especificaciones JPA para filtrar emprendimientos
 */
public class EmprendimientoSpecifications {
    
    public static Specification<EmprendimientoEntity> withFiltros(EmprendimientoFiltros filtros) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Filtro por nombre (búsqueda parcial, case insensitive)
            if (filtros.getNombre() != null && !filtros.getNombre().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombre")),
                    "%" + filtros.getNombre().toLowerCase() + "%"
                ));
            }
            
            // Filtro por sector
            if (filtros.getSectorId() != null) {
                Join<EmprendimientoEntity, SectorEntity> sectorJoin = root.join("sector");
                predicates.add(criteriaBuilder.equal(sectorJoin.get("id"), filtros.getSectorId()));
            }
            
            // Filtro por tipo de emprendimiento
            if (filtros.getTipoEmprendimientoId() != null) {
                Join<EmprendimientoEntity, TipoEmprendimientoEntity> tipoJoin = root.join("tipoEmprendimiento");
                predicates.add(criteriaBuilder.equal(tipoJoin.get("id"), filtros.getTipoEmprendimientoId()));
            }
            
            // Filtro por región
            if (filtros.getRegionId() != null) {
                Join<EmprendimientoEntity, RegionEntity> regionJoin = root.join("region");
                predicates.add(criteriaBuilder.equal(regionJoin.get("id"), filtros.getRegionId()));
            }
            
            // Filtro por departamento
            if (filtros.getDepartamento() != null && !filtros.getDepartamento().isEmpty()) {
                Join<EmprendimientoEntity, RegionEntity> regionJoin = root.join("region");
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(regionJoin.get("departamento")),
                    "%" + filtros.getDepartamento().toLowerCase() + "%"
                ));
            }
            
            // Filtro por municipio
            if (filtros.getMunicipio() != null && !filtros.getMunicipio().isEmpty()) {
                Join<EmprendimientoEntity, RegionEntity> regionJoin = root.join("region");
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(regionJoin.get("municipio")),
                    "%" + filtros.getMunicipio().toLowerCase() + "%"
                ));
            }
            
            // Filtro por estado
            if (filtros.getEstado() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("estado"),
                    EstadoEmprendimientoEntity.valueOf(filtros.getEstado().name())
                ));
            }
            
            // Filtro por fecha de creación (desde)
            if (filtros.getFechaCreacionDesde() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("fechaCreacion"),
                    filtros.getFechaCreacionDesde()
                ));
            }
            
            // Filtro por fecha de creación (hasta)
            if (filtros.getFechaCreacionHasta() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("fechaCreacion"),
                    filtros.getFechaCreacionHasta()
                ));
            }
            
            // Filtro por inversión mínima
            if (filtros.getInversionMinima() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("inversionInicial"),
                    filtros.getInversionMinima()
                ));
            }
            
            // Filtro por inversión máxima
            if (filtros.getInversionMaxima() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("inversionInicial"),
                    filtros.getInversionMaxima()
                ));
            }
            
            // Filtro por número mínimo de empleados
            if (filtros.getEmpleadosMinimo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("numeroEmpleados"),
                    filtros.getEmpleadosMinimo()
                ));
            }
            
            // Filtro por número máximo de empleados
            if (filtros.getEmpleadosMaximo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("numeroEmpleados"),
                    filtros.getEmpleadosMaximo()
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
