-- Script de creación de tablas para Red Comunitaria
-- Base de datos: red_comunitaria
-- Fecha: 2026-02-04

-- ============================================
-- Tabla: region
-- Descripción: Almacena las regiones (departamentos, municipios, corregimientos) de Colombia
-- ============================================
CREATE TABLE region (
    id BIGSERIAL PRIMARY KEY,
    departamento VARCHAR(100) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    corregimiento VARCHAR(100),
    poblacion BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Tabla: tipo_emprendimiento
-- Descripción: Tipos o categorías de emprendimientos
-- ============================================
CREATE TABLE tipo_emprendimiento (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    categoria VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Tabla: usuario
-- Descripción: Usuarios del sistema
-- ============================================
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    rol VARCHAR(20) DEFAULT 'USER' CHECK (rol IN ('USER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Tabla: emprendimiento
-- Descripción: Emprendimientos e innovaciones
-- ============================================
CREATE TABLE emprendimiento (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_creacion DATE NOT NULL,
    sector VARCHAR(100),
    numero_empleados INTEGER DEFAULT 0,
    inversion_inicial DECIMAL(15, 2),
    estado VARCHAR(20) DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO', 'EN_DESARROLLO')),
    usuario_id BIGINT NOT NULL,
    tipo_emprendimiento_id BIGINT NOT NULL,
    region_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emprendimiento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_emprendimiento_tipo FOREIGN KEY (tipo_emprendimiento_id) REFERENCES tipo_emprendimiento(id) ON DELETE RESTRICT,
    CONSTRAINT fk_emprendimiento_region FOREIGN KEY (region_id) REFERENCES region(id) ON DELETE RESTRICT
);

-- ============================================
-- Tabla: dato_historico
-- Descripción: Datos históricos de emprendimientos
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
    CONSTRAINT fk_dato_historico_emprendimiento FOREIGN KEY (emprendimiento_id) REFERENCES emprendimiento(id) ON DELETE CASCADE,
    CONSTRAINT unique_emprendimiento_anio UNIQUE (emprendimiento_id, anio)
);

-- ============================================
-- Tabla: password_reset_token
-- Descripción: Tokens para reseteo de contraseña
-- ============================================
CREATE TABLE password_reset_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_password_reset_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- ============================================
-- Comentarios en las tablas
-- ============================================
COMMENT ON TABLE region IS 'Regiones de Colombia: departamentos, municipios y corregimientos';
COMMENT ON TABLE tipo_emprendimiento IS 'Tipos y categorías de emprendimientos';
COMMENT ON TABLE usuario IS 'Usuarios registrados en el sistema';
COMMENT ON TABLE emprendimiento IS 'Emprendimientos e innovaciones registrados';
COMMENT ON TABLE dato_historico IS 'Datos históricos anuales de emprendimientos';
COMMENT ON TABLE password_reset_token IS 'Tokens para reseteo de contraseña de usuarios';

-- ============================================
-- Comentarios en columnas importantes
-- ============================================
COMMENT ON COLUMN usuario.rol IS 'Rol del usuario: USER o ADMIN';
COMMENT ON COLUMN usuario.activo IS 'Indica si el usuario está activo en el sistema';
COMMENT ON COLUMN emprendimiento.estado IS 'Estado del emprendimiento: ACTIVO, INACTIVO, EN_DESARROLLO';
COMMENT ON COLUMN emprendimiento.inversion_inicial IS 'Inversión inicial en pesos colombianos';
COMMENT ON COLUMN dato_historico.anio IS 'Año del registro histórico';
COMMENT ON COLUMN dato_historico.ingresos IS 'Ingresos anuales en pesos colombianos';
COMMENT ON COLUMN password_reset_token.token IS 'Token único para reseteo de contraseña';
COMMENT ON COLUMN password_reset_token.used IS 'Indica si el token ya fue utilizado';
