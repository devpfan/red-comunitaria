-- Importación de regiones desde DIVIPOLA (DANE)
-- Requiere un CSV normalizado con columnas:
-- departamento, municipio, corregimiento, poblacion
--
-- Ejemplo de uso (ejecutar manualmente):
-- psql -U postgres -d red_comunitaria -f docs_temp/scripts/07_import_divipola.sql \
--   -v csv_path='/ruta/al/divipola_normalizado.csv'

-- Crear tabla temporal de staging
CREATE TEMP TABLE tmp_divipola (
    departamento VARCHAR(100) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    corregimiento VARCHAR(100),
    poblacion BIGINT
);

-- Cargar CSV (usa variable :csv_path)
\copy tmp_divipola (departamento, municipio, corregimiento, poblacion) FROM :'csv_path' WITH (FORMAT csv, HEADER true);

-- Insertar evitando duplicados básicos
INSERT INTO region (departamento, municipio, corregimiento, poblacion, created_at, updated_at)
SELECT
    d.departamento,
    d.municipio,
    NULLIF(d.corregimiento, ''),
    d.poblacion,
    NOW(),
    NOW()
FROM tmp_divipola d
WHERE NOT EXISTS (
    SELECT 1
    FROM region r
    WHERE r.departamento = d.departamento
      AND r.municipio = d.municipio
      AND COALESCE(r.corregimiento, '') = COALESCE(NULLIF(d.corregimiento, ''), '')
);

-- Verificación rápida
SELECT COUNT(*) AS total_regiones FROM region;
