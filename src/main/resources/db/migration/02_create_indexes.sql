-- Script de creación de índices para Red Comunitaria
-- Base de datos: red_comunitaria
-- Fecha: 2026-02-04

-- ============================================
-- Índices para tabla: region
-- ============================================
CREATE INDEX idx_region_departamento ON region(departamento);
CREATE INDEX idx_region_municipio ON region(municipio);
CREATE INDEX idx_region_dept_mun ON region(departamento, municipio);

-- ============================================
-- Índices para tabla: tipo_emprendimiento
-- ============================================
CREATE INDEX idx_tipo_emprendimiento_categoria ON tipo_emprendimiento(categoria);

-- ============================================
-- Índices para tabla: usuario
-- ============================================
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_activo ON usuario(activo);
CREATE INDEX idx_usuario_rol ON usuario(rol);

-- ============================================
-- Índices para tabla: emprendimiento
-- ============================================
CREATE INDEX idx_emprendimiento_usuario_id ON emprendimiento(usuario_id);
CREATE INDEX idx_emprendimiento_tipo_id ON emprendimiento(tipo_emprendimiento_id);
CREATE INDEX idx_emprendimiento_region_id ON emprendimiento(region_id);
CREATE INDEX idx_emprendimiento_estado ON emprendimiento(estado);
CREATE INDEX idx_emprendimiento_fecha_creacion ON emprendimiento(fecha_creacion);
CREATE INDEX idx_emprendimiento_sector ON emprendimiento(sector);

-- Índices compuestos para consultas frecuentes
CREATE INDEX idx_emprendimiento_tipo_region ON emprendimiento(tipo_emprendimiento_id, region_id);
CREATE INDEX idx_emprendimiento_estado_fecha ON emprendimiento(estado, fecha_creacion);

-- ============================================
-- Índices para tabla: dato_historico
-- ============================================
CREATE INDEX idx_dato_historico_emprendimiento_id ON dato_historico(emprendimiento_id);
CREATE INDEX idx_dato_historico_anio ON dato_historico(anio);
CREATE INDEX idx_dato_historico_emp_anio ON dato_historico(emprendimiento_id, anio);

-- ============================================
-- Índices para tabla: password_reset_token
-- ============================================
CREATE INDEX idx_password_reset_token_token ON password_reset_token(token);
CREATE INDEX idx_password_reset_token_usuario_id ON password_reset_token(usuario_id);
CREATE INDEX idx_password_reset_token_expiry_date ON password_reset_token(expiry_date);
CREATE INDEX idx_password_reset_token_used ON password_reset_token(used);

-- Índice compuesto para búsqueda de tokens válidos
CREATE INDEX idx_password_reset_valid ON password_reset_token(usuario_id, used, expiry_date);
