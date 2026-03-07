-- Script de creación de tabla dato_historico
-- Fecha: 2026-02-07
-- Descripción: Almacena datos históricos anuales de emprendimientos (ingresos, empleos, innovaciones)

-- ============================================
-- Tabla: dato_historico
-- Descripción: Datos históricos de emprendimientos por año
-- ============================================
CREATE TABLE dato_historico (
    id BIGSERIAL PRIMARY KEY,
    emprendimiento_id BIGINT NOT NULL,
    anio INTEGER NOT NULL,
    ingresos DECIMAL(15, 2),
    empleos INTEGER,
    innovaciones INTEGER,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dato_historico_emprendimiento 
        FOREIGN KEY (emprendimiento_id) REFERENCES emprendimiento(id) ON DELETE CASCADE,
    CONSTRAINT unique_emprendimiento_anio UNIQUE (emprendimiento_id, anio),
    CONSTRAINT check_anio_valido CHECK (anio >= 1900 AND anio <= 2100),
    CONSTRAINT check_empleos_positivo CHECK (empleos >= 0),
    CONSTRAINT check_innovaciones_positivo CHECK (innovaciones >= 0)
);

-- Índices para búsquedas rápidas
CREATE INDEX idx_dato_historico_emprendimiento ON dato_historico(emprendimiento_id);
CREATE INDEX idx_dato_historico_anio ON dato_historico(anio);
CREATE INDEX idx_dato_historico_emprendimiento_anio ON dato_historico(emprendimiento_id, anio);

-- Comentarios
COMMENT ON TABLE dato_historico IS 'Datos históricos anuales de emprendimientos (ingresos, empleos generados, innovaciones)';
COMMENT ON COLUMN dato_historico.emprendimiento_id IS 'Referencia al emprendimiento';
COMMENT ON COLUMN dato_historico.anio IS 'Año del registro histórico';
COMMENT ON COLUMN dato_historico.ingresos IS 'Ingresos anuales en pesos colombianos';
COMMENT ON COLUMN dato_historico.empleos IS 'Número de empleos generados en ese año';
COMMENT ON COLUMN dato_historico.innovaciones IS 'Número de innovaciones implementadas';
