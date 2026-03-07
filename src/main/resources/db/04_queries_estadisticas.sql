-- Script de consultas de estadísticas para Red Comunitaria
-- Base de datos: red_comunitaria
-- Fecha: 2026-02-04
-- Descripción: Queries para los requerimientos de estadísticas (Nivel 2 y 3)

-- ============================================
-- Query 1: Producción total por tipo y región
-- Descripción: Obtener la producción total de emprendimientos por tipo, agrupada por regiones
-- ============================================
SELECT 
    r.departamento,
    r.municipio,
    te.nombre as tipo_emprendimiento,
    te.categoria,
    COUNT(e.id) as total_emprendimientos,
    COALESCE(SUM(dh.empleos), 0) as total_empleos,
    COALESCE(SUM(dh.ingresos), 0) as ingresos_totales,
    COALESCE(AVG(dh.ingresos), 0) as ingreso_promedio
FROM emprendimiento e
JOIN region r ON e.region_id = r.id
JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
WHERE e.estado = 'ACTIVO'
GROUP BY r.departamento, r.municipio, te.nombre, te.categoria
ORDER BY total_emprendimientos DESC;

-- ============================================
-- Query 2: Porcentaje de emprendimientos por región
-- Descripción: Calcular el porcentaje de emprendimientos por cada región
-- ============================================
SELECT 
    r.departamento,
    r.municipio,
    COUNT(e.id) as cantidad_emprendimientos,
    ROUND(
        COUNT(e.id) * 100.0 / NULLIF((SELECT COUNT(*) FROM emprendimiento WHERE estado = 'ACTIVO'), 0), 
        2
    ) as porcentaje
FROM region r
LEFT JOIN emprendimiento e ON r.id = e.region_id AND e.estado = 'ACTIVO'
GROUP BY r.id, r.departamento, r.municipio
HAVING COUNT(e.id) > 0
ORDER BY porcentaje DESC;

-- ============================================
-- Query 3: Top 10 departamentos con mayor emprendimiento
-- Descripción: Obtener los 10 departamentos con mayor cantidad de emprendimientos
-- ============================================
SELECT 
    r.departamento,
    COUNT(DISTINCT e.id) as total_emprendimientos,
    COALESCE(SUM(dh.empleos), 0) as empleos_generados,
    COALESCE(AVG(dh.ingresos), 0) as ingreso_promedio,
    COUNT(DISTINCT e.tipo_emprendimiento_id) as tipos_emprendimiento,
    COUNT(DISTINCT e.usuario_id) as total_emprendedores
FROM region r
LEFT JOIN emprendimiento e ON r.id = e.region_id AND e.estado = 'ACTIVO'
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
GROUP BY r.departamento
HAVING COUNT(DISTINCT e.id) > 0
ORDER BY total_emprendimientos DESC
LIMIT 10;

-- ============================================
-- Query 4: Emprendimientos con filtros dinámicos
-- Descripción: Listar emprendimientos según filtros aplicables
-- Nota: Los parámetros :tipoId, :regionId, :estado, :fechaInicio, :fechaFin 
-- serán reemplazados en tiempo de ejecución por JPA
-- ============================================
SELECT 
    e.id,
    e.nombre,
    e.descripcion,
    e.fecha_creacion,
    e.sector,
    e.numero_empleados,
    e.inversion_inicial,
    e.estado,
    u.nombre || ' ' || u.apellido as emprendedor,
    te.nombre as tipo_emprendimiento,
    r.departamento || ' - ' || r.municipio as ubicacion
FROM emprendimiento e
JOIN usuario u ON e.usuario_id = u.id
JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
JOIN region r ON e.region_id = r.id
WHERE 
    (:tipoId IS NULL OR e.tipo_emprendimiento_id = :tipoId)
    AND (:regionId IS NULL OR e.region_id = :regionId)
    AND (:estado IS NULL OR e.estado = :estado)
    AND (:fechaInicio IS NULL OR e.fecha_creacion >= :fechaInicio)
    AND (:fechaFin IS NULL OR e.fecha_creacion <= :fechaFin)
    AND (:sector IS NULL OR e.sector ILIKE '%' || :sector || '%')
ORDER BY e.fecha_creacion DESC;

-- ============================================
-- Query 5: Dashboard general - Métricas principales
-- Descripción: Obtener métricas generales para dashboard
-- ============================================
SELECT 
    COUNT(DISTINCT e.id) as total_emprendimientos,
    COUNT(DISTINCT e.usuario_id) as total_emprendedores,
    COALESCE(SUM(e.numero_empleados), 0) as total_empleos_directos,
    COALESCE(SUM(dh.empleos), 0) as total_empleos_historicos,
    COALESCE(SUM(dh.ingresos), 0) as ingresos_totales,
    COALESCE(AVG(e.inversion_inicial), 0) as inversion_promedio,
    COUNT(DISTINCT r.departamento) as departamentos_cubiertos,
    COUNT(DISTINCT te.id) as tipos_emprendimiento
FROM emprendimiento e
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
LEFT JOIN region r ON e.region_id = r.id
LEFT JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
WHERE e.estado = 'ACTIVO';

-- ============================================
-- Query 6: Emprendimientos por sector
-- Descripción: Agrupación de emprendimientos por sector
-- ============================================
SELECT 
    e.sector,
    COUNT(e.id) as total_emprendimientos,
    COALESCE(SUM(e.numero_empleados), 0) as empleos_directos,
    COALESCE(AVG(e.inversion_inicial), 0) as inversion_promedio
FROM emprendimiento e
WHERE e.estado = 'ACTIVO' AND e.sector IS NOT NULL
GROUP BY e.sector
ORDER BY total_emprendimientos DESC;

-- ============================================
-- Query 7: Evolución histórica por año
-- Descripción: Datos históricos agregados por año
-- ============================================
SELECT 
    dh.anio,
    COUNT(DISTINCT dh.emprendimiento_id) as emprendimientos_con_datos,
    COALESCE(SUM(dh.ingresos), 0) as ingresos_totales,
    COALESCE(SUM(dh.empleos), 0) as empleos_totales,
    COALESCE(SUM(dh.innovaciones), 0) as innovaciones_totales,
    COALESCE(AVG(dh.ingresos), 0) as ingreso_promedio
FROM dato_historico dh
GROUP BY dh.anio
ORDER BY dh.anio DESC;

-- ============================================
-- Query 8: Top emprendedores
-- Descripción: Usuarios con más emprendimientos activos
-- ============================================
SELECT 
    u.id,
    u.nombre || ' ' || u.apellido as nombre_completo,
    u.email,
    COUNT(e.id) as total_emprendimientos,
    COALESCE(SUM(e.numero_empleados), 0) as empleos_generados,
    COALESCE(SUM(e.inversion_inicial), 0) as inversion_total
FROM usuario u
JOIN emprendimiento e ON u.id = e.usuario_id
WHERE e.estado = 'ACTIVO'
GROUP BY u.id, u.nombre, u.apellido, u.email
ORDER BY total_emprendimientos DESC
LIMIT 10;

-- ============================================
-- Query 9: Emprendimientos recientes
-- Descripción: Últimos emprendimientos creados (últimos 30 días)
-- ============================================
SELECT 
    e.id,
    e.nombre,
    e.fecha_creacion,
    u.nombre || ' ' || u.apellido as emprendedor,
    te.nombre as tipo,
    r.departamento,
    r.municipio
FROM emprendimiento e
JOIN usuario u ON e.usuario_id = u.id
JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
JOIN region r ON e.region_id = r.id
WHERE e.fecha_creacion >= CURRENT_DATE - INTERVAL '30 days'
ORDER BY e.fecha_creacion DESC;

-- ============================================
-- Query 10: Búsqueda en tiempo real
-- Descripción: Búsqueda de emprendimientos por término (nombre o descripción)
-- ============================================
SELECT 
    e.id,
    e.nombre,
    e.descripcion,
    e.sector,
    e.estado,
    u.nombre || ' ' || u.apellido as emprendedor,
    te.nombre as tipo_emprendimiento,
    r.departamento || ' - ' || r.municipio as ubicacion
FROM emprendimiento e
JOIN usuario u ON e.usuario_id = u.id
JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
JOIN region r ON e.region_id = r.id
WHERE 
    (e.nombre ILIKE '%' || :searchTerm || '%' 
     OR e.descripcion ILIKE '%' || :searchTerm || '%'
     OR e.sector ILIKE '%' || :searchTerm || '%')
    AND e.estado = 'ACTIVO'
ORDER BY e.fecha_creacion DESC
LIMIT 20;
