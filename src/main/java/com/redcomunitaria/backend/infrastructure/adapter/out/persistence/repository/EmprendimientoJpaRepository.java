package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EmprendimientoEntity;

/**
 * Repositorio JPA: Emprendimiento con soporte para búsqueda dinámica
 */
@Repository
public interface EmprendimientoJpaRepository extends JpaRepository<EmprendimientoEntity, Long>, 
                                                      JpaSpecificationExecutor<EmprendimientoEntity> {
    
    /**
     * Estadísticas agrupadas por tipo de emprendimiento
     */
    @Query("""
        SELECT 
            te.id as tipoId,
            te.nombre as tipoNombre,
            COUNT(e.id) as cantidad,
            COALESCE(SUM(e.inversionInicial), 0) as inversionTotal,
            COALESCE(AVG(e.inversionInicial), 0) as inversionPromedio,
            COALESCE(SUM(e.numeroEmpleados), 0) as empleadosTotal,
            COALESCE(AVG(e.numeroEmpleados), 0) as empleadosPromedio
        FROM EmprendimientoEntity e
        JOIN e.tipoEmprendimiento te
        GROUP BY te.id, te.nombre
        ORDER BY cantidad DESC
    """)
    List<Object[]> findEstadisticasPorTipo();
    
    /**
     * Estadísticas agrupadas por región (departamento y municipio)
     */
    @Query("""
        SELECT 
            r.id as regionId,
            r.departamento as departamento,
            r.municipio as municipio,
            COUNT(e.id) as cantidad,
            COALESCE(SUM(e.inversionInicial), 0) as inversionTotal,
            COALESCE(AVG(e.inversionInicial), 0) as inversionPromedio,
            COALESCE(SUM(e.numeroEmpleados), 0) as empleadosTotal,
            COALESCE(AVG(e.numeroEmpleados), 0) as empleadosPromedio
        FROM EmprendimientoEntity e
        JOIN e.region r
        GROUP BY r.id, r.departamento, r.municipio
        ORDER BY cantidad DESC
    """)
    List<Object[]> findEstadisticasPorRegion();
    
    /**
     * Top N departamentos con más emprendimientos
     */
    @Query(value = """
        SELECT 
            r.departamento as departamento,
            COUNT(e.id) as cantidadEmprendimientos,
            COALESCE(SUM(e.numero_empleados), 0) as empleadosTotal,
            COALESCE(SUM(e.inversion_inicial), 0) as inversionTotal
        FROM emprendimiento e
        JOIN region r ON e.region_id = r.id
        GROUP BY r.departamento
        ORDER BY cantidadEmprendimientos DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findTopDepartamentos(int limit);
    
    /**
     * Contar emprendimientos por estado
     */
    @Query("""
        SELECT e.estado, COUNT(e.id)
        FROM EmprendimientoEntity e
        GROUP BY e.estado
    """)
    List<Object[]> countByEstado();
}

