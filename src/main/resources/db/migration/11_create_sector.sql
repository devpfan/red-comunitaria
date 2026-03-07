-- Script de creación de tabla sector
-- Fecha: 2026-02-07
-- Descripción: Catálogo de sectores económicos según clasificación CIIU de Colombia

-- ============================================
-- Tabla: sector
-- Descripción: Sectores económicos estandarizados
-- ============================================
CREATE TABLE sector (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    descripcion TEXT,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar los 19 sectores económicos según CIIU Colombia
INSERT INTO sector (codigo, nombre, descripcion) VALUES
('A', 'Agricultura, ganadería, silvicultura y pesca', 'Actividades primarias relacionadas con agricultura, ganadería, aprovechamiento forestal y pesca'),
('B', 'Explotación de minas y canteras', 'Extracción de minerales de origen natural: sólidos (carbón y otros minerales), líquidos (petróleo) o gaseosos (gas natural)'),
('C', 'Industrias manufactureras', 'Transformación física o química de materiales, sustancias o componentes en productos nuevos'),
('D', 'Suministro de electricidad, gas, vapor y aire acondicionado', 'Actividades de suministro de energía eléctrica, gas natural, vapor, agua caliente y similares'),
('E', 'Suministro de agua; evacuación de aguas residuales, gestión de desechos y descontaminación', 'Actividades relacionadas con el abastecimiento de agua, recolección y tratamiento de aguas residuales'),
('F', 'Construcción', 'Actividades de construcción general y especializada de edificios y obras de ingeniería civil'),
('G', 'Comercio al por mayor y al por menor; reparación de vehículos', 'Reventa (venta sin transformación) de mercancías nuevas y usadas'),
('H', 'Transporte y almacenamiento', 'Actividades relacionadas con el transporte de pasajeros y carga, regular o no regular'),
('I', 'Alojamiento y servicios de comida', 'Suministro de alojamiento temporal y servicios de expendio de comidas y bebidas'),
('J', 'Información y comunicaciones', 'Producción y distribución de información y productos culturales, suministro de medios de transmisión'),
('K', 'Actividades financieras y de seguros', 'Actividades de intermediación financiera, seguros, pensiones y actividades auxiliares'),
('L', 'Actividades inmobiliarias', 'Explotación de bienes raíces propios o arrendados, actuación como agentes inmobiliarios'),
('M', 'Actividades profesionales, científicas y técnicas', 'Actividades especializadas profesionales, científicas y técnicas'),
('N', 'Actividades de servicios administrativos y de apoyo', 'Actividades de apoyo a las operaciones de otras empresas'),
('O', 'Administración pública y defensa; planes de seguridad social', 'Actividades de carácter gubernamental, administración del Estado'),
('P', 'Educación', 'Actividades de enseñanza en todos los niveles y para todas las disciplinas'),
('Q', 'Actividades de atención de la salud humana y de asistencia social', 'Actividades de hospitales, médicos, odontólogos, actividades de asistencia social'),
('R', 'Actividades artísticas, de entretenimiento y recreación', 'Actividades creativas, artísticas, de entretenimiento, bibliotecas, museos, deportes'),
('S', 'Otras actividades de servicios', 'Actividades de asociaciones, reparación de computadores, servicios personales');

-- Índices
CREATE INDEX idx_sector_codigo ON sector(codigo);
CREATE INDEX idx_sector_activo ON sector(activo);

-- Comentarios
COMMENT ON TABLE sector IS 'Catálogo de sectores económicos según clasificación CIIU Colombia';
COMMENT ON COLUMN sector.codigo IS 'Código del sector según CIIU (A-S)';
COMMENT ON COLUMN sector.nombre IS 'Nombre completo del sector económico';
COMMENT ON COLUMN sector.descripcion IS 'Descripción detallada de las actividades del sector';
COMMENT ON COLUMN sector.activo IS 'Indica si el sector está disponible para selección';
