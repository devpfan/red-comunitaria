-- Script de migración: agregar relación de sector a emprendimiento
-- Fecha: 2026-02-07
-- Descripción: Reemplaza el campo sector (varchar) por sector_id (FK)

-- 1. Agregar nueva columna sector_id
ALTER TABLE emprendimiento ADD COLUMN sector_id BIGINT;

-- 2. Agregar constraint de FK
ALTER TABLE emprendimiento 
ADD CONSTRAINT fk_emprendimiento_sector 
FOREIGN KEY (sector_id) REFERENCES sector(id);

-- 3. Migrar datos existentes: asignar sector por defecto basado en el texto actual
-- Asumiendo que el emprendimiento actual podría ser del sector C (Industrias manufactureras)
-- Puedes ajustar esto según el sector real del emprendimiento
UPDATE emprendimiento 
SET sector_id = (SELECT id FROM sector WHERE codigo = 'C' LIMIT 1)
WHERE sector_id IS NULL;

-- 4. Hacer la columna NOT NULL ahora que tiene datos
ALTER TABLE emprendimiento ALTER COLUMN sector_id SET NOT NULL;

-- 5. Eliminar la columna antigua sector (varchar)
ALTER TABLE emprendimiento DROP COLUMN sector;

-- 6. Crear índice para mejorar búsquedas
CREATE INDEX idx_emprendimiento_sector ON emprendimiento(sector_id);

-- Comentarios
COMMENT ON COLUMN emprendimiento.sector_id IS 'Referencia al sector económico del emprendimiento';
