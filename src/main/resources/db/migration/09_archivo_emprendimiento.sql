-- Script de creación de tabla archivo_emprendimiento
-- Fecha: 2026-02-07
-- Descripción: Gestión de archivos adjuntos a emprendimientos

-- ============================================
-- Tabla: archivo_emprendimiento
-- Descripción: Almacena metadata de archivos (imágenes, documentos, videos)
-- ============================================
CREATE TABLE archivo_emprendimiento (
    id BIGSERIAL PRIMARY KEY,
    emprendimiento_id BIGINT NOT NULL,
    nombre_original VARCHAR(255) NOT NULL,
    nombre_almacenado VARCHAR(255) NOT NULL UNIQUE,
    tipo_archivo VARCHAR(50) NOT NULL CHECK (tipo_archivo IN ('logo', 'imagen', 'documento', 'video', 'otro')),
    mime_type VARCHAR(100) NOT NULL,
    tamanio BIGINT NOT NULL,
    ruta_archivo TEXT NOT NULL,
    descripcion TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_archivo_emprendimiento FOREIGN KEY (emprendimiento_id) 
        REFERENCES emprendimiento(id) ON DELETE CASCADE
);

-- Índices para búsquedas rápidas
CREATE INDEX idx_archivo_emprendimiento_emprendimiento ON archivo_emprendimiento(emprendimiento_id);
CREATE INDEX idx_archivo_emprendimiento_tipo ON archivo_emprendimiento(tipo_archivo);

-- Comentarios
COMMENT ON TABLE archivo_emprendimiento IS 'Archivos adjuntos a emprendimientos (imágenes, documentos, videos)';
COMMENT ON COLUMN archivo_emprendimiento.nombre_original IS 'Nombre original del archivo subido por el usuario';
COMMENT ON COLUMN archivo_emprendimiento.nombre_almacenado IS 'Nombre único generado para evitar colisiones (UUID + extensión)';
COMMENT ON COLUMN archivo_emprendimiento.tipo_archivo IS 'Categoría: logo, imagen, documento, video, otro';
COMMENT ON COLUMN archivo_emprendimiento.mime_type IS 'Tipo MIME del archivo (image/jpeg, application/pdf, etc.)';
COMMENT ON COLUMN archivo_emprendimiento.tamanio IS 'Tamaño del archivo en bytes';
COMMENT ON COLUMN archivo_emprendimiento.ruta_archivo IS 'Ruta relativa donde se almacena el archivo';
