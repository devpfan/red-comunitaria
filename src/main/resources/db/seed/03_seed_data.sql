-- Script de datos iniciales para Red Comunitaria
-- Base de datos: red_comunitaria
-- Fecha: 2026-02-04

-- ============================================
-- Datos iniciales: tipo_emprendimiento
-- ============================================
INSERT INTO tipo_emprendimiento (nombre, descripcion, categoria) VALUES
('Tecnología', 'Emprendimientos relacionados con tecnología e innovación digital', 'TIC'),
('Agricultura Sostenible', 'Proyectos agrícolas con enfoque sostenible', 'Agropecuario'),
('Comercio', 'Negocios de comercio y ventas', 'Comercial'),
('Turismo', 'Emprendimientos relacionados con turismo y hospitalidad', 'Servicios'),
('Educación', 'Proyectos educativos e innovación pedagógica', 'Educativo'),
('Salud', 'Emprendimientos del sector salud y bienestar', 'Salud'),
('Manufactura', 'Producción de bienes y manufactura', 'Industrial'),
('Servicios Financieros', 'Fintech y servicios financieros', 'Financiero'),
('Energías Renovables', 'Proyectos de energía limpia y renovable', 'Ambiental'),
('Gastronomía', 'Emprendimientos gastronómicos y alimentarios', 'Alimentario');

-- ============================================
-- Datos iniciales: region (Principales departamentos y municipios de Colombia)
-- ============================================
-- Bogotá D.C.
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Bogotá D.C.', 'Bogotá', NULL, 7181469);

-- Antioquia
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Antioquia', 'Medellín', NULL, 2569007),
('Antioquia', 'Bello', NULL, 483754),
('Antioquia', 'Itagüí', NULL, 281853),
('Antioquia', 'Envigado', NULL, 235689);

-- Valle del Cauca
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Valle del Cauca', 'Cali', NULL, 2252616),
('Valle del Cauca', 'Palmira', NULL, 303815),
('Valle del Cauca', 'Buenaventura', NULL, 428863);

-- Atlántico
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Atlántico', 'Barranquilla', NULL, 1232766),
('Atlántico', 'Soledad', NULL, 655734);

-- Santander
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Santander', 'Bucaramanga', NULL, 603167),
('Santander', 'Floridablanca', NULL, 271508),
('Santander', 'Girón', NULL, 191469);

-- Bolívar
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Bolívar', 'Cartagena', NULL, 1028736);

-- Cundinamarca
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Cundinamarca', 'Soacha', NULL, 728704),
('Cundinamarca', 'Facatativá', NULL, 147454),
('Cundinamarca', 'Chía', NULL, 132897),
('Cundinamarca', 'Zipaquirá', NULL, 137833);

-- Norte de Santander
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Norte de Santander', 'Cúcuta', NULL, 711715);

-- Risaralda
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Risaralda', 'Pereira', NULL, 488839);

-- Quindío
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Quindío', 'Armenia', NULL, 304936);

-- Tolima
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Tolima', 'Ibagué', NULL, 563298);

-- Meta
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Meta', 'Villavicencio', NULL, 534275);

-- Caldas
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Caldas', 'Manizales', NULL, 434403);

-- Cauca
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Cauca', 'Popayán', NULL, 318059);

-- Nariño
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Nariño', 'Pasto', NULL, 474497);

-- Magdalena
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Magdalena', 'Santa Marta', NULL, 523136);

-- Huila
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Huila', 'Neiva', NULL, 357642);

-- Córdoba
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Córdoba', 'Montería', NULL, 478980);

-- Cesar
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Cesar', 'Valledupar', NULL, 493462);

-- Boyacá
INSERT INTO region (departamento, municipio, corregimiento, poblacion) VALUES
('Boyacá', 'Tunja', NULL, 217500),
('Boyacá', 'Duitama', NULL, 136667);

-- ============================================
-- Datos iniciales: usuario (Usuario administrador)
-- ============================================
-- Password: admin123 (encriptado con BCrypt)
-- Nota: En producción, la contraseña debe ser más segura y encriptada
INSERT INTO usuario (nombre, apellido, email, password, telefono, activo, rol) VALUES
('Administrador', 'Sistema', 'admin@redcomunitaria.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '3001234567', true, 'ADMIN');

-- Usuario de prueba
-- Password: user123 (encriptado con BCrypt)
INSERT INTO usuario (nombre, apellido, email, password, telefono, activo, rol) VALUES
('Juan', 'Pérez', 'juan.perez@example.com', '$2a$10$5QHE/SdQ4Zw0Qhs5oMH0j.9VHh1HG3E1m5W5fMnS4QY8xZsxXqGHa', '3001234568', true, 'USER');

-- ============================================
-- Nota sobre las contraseñas
-- ============================================
-- Las contraseñas están encriptadas con BCrypt
-- admin123: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- user123: $2a$10$5QHE/SdQ4Zw0Qhs5oMH0j.9VHh1HG3E1m5W5fMnS4QY8xZsxXqGHa
