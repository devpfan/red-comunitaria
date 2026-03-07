# Estructura de Base de Datos - Red Comunitaria

## 🗄️ Motor de Base de Datos
**PostgreSQL 15+**

## 📊 Modelo Entidad-Relación (ER)

```
┌─────────────────┐
│    usuario      │
├─────────────────┤
│ id (PK)         │
│ nombre          │
│ apellido        │
│ email (UNIQUE)  │
│ password        │
│ telefono        │
│ fecha_registro  │
│ activo          │
│ rol             │
│ created_at      │
│ updated_at      │
└────────┬────────┘
         │ 1
         │
         │ N
         ▼
┌─────────────────────────┐
│   emprendimiento        │
├─────────────────────────┤
│ id (PK)                 │───┐
│ nombre                  │   │
│ descripcion             │   │
│ fecha_creacion          │   │ N
│ numero_empleados        │   │
│ inversion_inicial       │   │
│ estado                  │   │
│ usuario_id (FK)         │───┘
│ tipo_emprendimiento_id  │───┐
│ region_id (FK)          │───┼───────┐
│ sector_id (FK)          │───┼───────┼───────┐
│ created_at              │   │       │       │
│ updated_at              │   │       │       │
└────────┬────────────────┘   │       │       │
         │ 1                  │       │       │
         │                    │       │       │
         │ N                  │ N     │ N     │ N
         ▼                    ▼       ▼       ▼
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│  dato_historico     │  │ tipo_emprendimiento │  │      region         │  │      sector         │
├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤
│ id (PK)             │  │ id (PK)             │  │ id (PK)             │  │ id (PK)             │
│ emprendimiento_id   │  │ nombre (UNIQUE)     │  │ codigo_divipola     │  │ codigo (UNIQUE)     │
│ anio                │  │ descripcion         │  │ departamento        │  │ nombre (UNIQUE)     │
│ ingresos            │  │ categoria           │  │ municipio           │  │ descripcion         │
│ empleos             │  │ created_at          │  │ corregimiento       │  │ activo              │
│ innovaciones        │  │ updated_at          │  │ poblacion           │  │ created_at          │
│ fecha_registro      │  └─────────────────────┘  │ created_at          │  │ updated_at          │
│ created_at          │                           │ updated_at          │  └─────────────────────┘
│ updated_at          │                           └─────────────────────┘
└─────────────────────┘

         │ 1
         │
         │ N
         ▼
┌──────────────────────────┐
│ archivo_emprendimiento   │
├──────────────────────────┤
│ id (PK)                  │
│ emprendimiento_id (FK)   │
│ nombre_original          │
│ nombre_almacenado        │
│ tipo_archivo             │
│ mime_type                │
│ tamanio                  │
│ ruta_archivo             │
│ descripcion              │
│ created_at               │
│ updated_at               │
└──────────────────────────┘

┌──────────────────────────┐
│ password_reset_token     │
├──────────────────────────┤
│ id (PK)                  │
│ usuario_id (FK)          │
│ code (6 digits)          │
│ expiry_date              │
│ used                     │
│ created_at               │
└──────────────────────────┘
```

---

## 📋 Descripción de Tablas

### 1. **usuario**
Almacena información de los usuarios del sistema.

| Campo           | Tipo           | Restricciones        | Descripción                          |
|----------------|----------------|----------------------|--------------------------------------|
| id             | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| nombre         | VARCHAR(100)   | NOT NULL             | Nombre del usuario                   |
| apellido       | VARCHAR(100)   | NOT NULL             | Apellido del usuario                 |
| email          | VARCHAR(255)   | NOT NULL, UNIQUE     | Email único de login                 |
| password       | VARCHAR(255)   | NOT NULL             | Contraseña encriptada (BCrypt)       |
| telefono       | VARCHAR(20)    |                      | Teléfono de contacto                 |
| fecha_registro | TIMESTAMP      | DEFAULT NOW()        | Fecha de registro                    |
| activo         | BOOLEAN        | DEFAULT TRUE         | Usuario activo/desactivado           |
| rol            | VARCHAR(20)    | NOT NULL             | Rol: USER, ADMIN                     |
| created_at     | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación del registro       |
| updated_at     | TIMESTAMP      |                      | Fecha de última actualización        |

**Índices:**
- `idx_usuario_email` on `email`
- `idx_usuario_activo` on `activo`

---

### 2. **emprendimiento**
Información principal de los emprendimientos registrados.

| Campo                   | Tipo           | Restricciones        | Descripción                          |
|------------------------|----------------|----------------------|--------------------------------------|
| id                     | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| nombre                 | VARCHAR(255)   | NOT NULL             | Nombre del emprendimiento            |
| descripcion            | TEXT           |                      | Descripción detallada                |
| fecha_creacion         | DATE           | NOT NULL             | Fecha de inicio del emprendimiento   |
| numero_empleados       | INTEGER        | CHECK (>= 0)         | Cantidad de empleados                |
| inversion_inicial      | DECIMAL(15,2)  | CHECK (>= 0)         | Inversión inicial en COP             |
| estado                 | VARCHAR(20)    | NOT NULL             | ACTIVO, INACTIVO, EN_DESARROLLO      |
| usuario_id             | BIGINT         | FOREIGN KEY          | Propietario del emprendimiento       |
| tipo_emprendimiento_id | BIGINT         | FOREIGN KEY          | Tipo de emprendimiento               |
| region_id              | BIGINT         | FOREIGN KEY          | Ubicación geográfica                 |
| sector_id              | BIGINT         | FOREIGN KEY          | Sector económico (CIIU)              |
| created_at             | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación del registro       |
| updated_at             | TIMESTAMP      |                      | Fecha de última actualización        |

**Índices:**
- `idx_emprendimiento_usuario` on `usuario_id`
- `idx_emprendimiento_tipo` on `tipo_emprendimiento_id`
- `idx_emprendimiento_region` on `region_id`
- `idx_emprendimiento_sector` on `sector_id`
- `idx_emprendimiento_estado` on `estado`
- `idx_emprendimiento_fecha` on `fecha_creacion`

**Relaciones:**
- `FK_emprendimiento_usuario` → `usuario(id)` ON DELETE CASCADE
- `FK_emprendimiento_tipo` → `tipo_emprendimiento(id)`
- `FK_emprendimiento_region` → `region(id)`
- `FK_emprendimiento_sector` → `sector(id)`

---

### 3. **tipo_emprendimiento**
Catálogo de tipos de emprendimientos.

| Campo       | Tipo           | Restricciones        | Descripción                          |
|------------|----------------|----------------------|--------------------------------------|
| id         | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| nombre     | VARCHAR(100)   | NOT NULL, UNIQUE     | Nombre del tipo                      |
| descripcion| TEXT           |                      | Descripción del tipo                 |
| categoria  | VARCHAR(100)   |                      | Categoría general                    |
| created_at | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación del registro       |
| updated_at | TIMESTAMP      |                      | Fecha de última actualización        |

**Datos iniciales:**
- Tecnológico
- Agrícola
- Comercial
- Industrial
- Servicios
- Social

---

### 4. **region**
División política de Colombia (DIVIPOLA).

| Campo            | Tipo           | Restricciones        | Descripción                          |
|-----------------|----------------|----------------------|--------------------------------------|
| id              | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| codigo_divipola | VARCHAR(20)    |                      | Código DANE                          |
| departamento    | VARCHAR(100)   | NOT NULL             | Nombre del departamento              |
| municipio       | VARCHAR(100)   | NOT NULL             | Nombre del municipio                 |
| corregimiento   | VARCHAR(100)   |                      | Corregimiento (opcional)             |
| poblacion       | BIGINT         |                      | Población del municipio              |
| created_at      | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación del registro       |
| updated_at      | TIMESTAMP      |                      | Fecha de última actualización        |

**Índices:**
- `idx_region_departamento` on `departamento`
- `idx_region_municipio` on `municipio`
- `idx_region_codigo` on `codigo_divipola`

**Datos:** 1,120 municipios colombianos (cargados desde DIVIPOLA)

---

### 5. **sector**
Clasificación por sectores económicos (CIIU Rev. 4).

| Campo       | Tipo           | Restricciones        | Descripción                          |
|------------|----------------|----------------------|--------------------------------------|
| id         | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| codigo     | VARCHAR(10)    | NOT NULL, UNIQUE     | Código CIIU (ej: A, B, C...)         |
| nombre     | VARCHAR(255)   | NOT NULL, UNIQUE     | Nombre del sector                    |
| descripcion| TEXT           |                      | Descripción detallada                |
| activo     | BOOLEAN        | DEFAULT TRUE         | Sector activo/desactivado            |
| created_at | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación                    |
| updated_at | TIMESTAMP      |                      | Fecha de actualización               |

**Datos iniciales (19 sectores CIIU):**
- A: Agricultura, ganadería, silvicultura y pesca
- B: Explotación de minas y canteras
- C: Industrias manufactureras
- D: Suministro de electricidad, gas, vapor y aire acondicionado
- E: Distribución de agua; evacuación y tratamiento de aguas residuales
- F: Construcción
- G: Comercio al por mayor y al por menor
- H: Transporte y almacenamiento
- I: Alojamiento y servicios de comida
- J: Información y comunicaciones
- K: Actividades financieras y de seguros
- L: Actividades inmobiliarias
- M: Actividades profesionales, científicas y técnicas
- N: Actividades de servicios administrativos y de apoyo
- O: Administración pública y defensa
- P: Educación
- Q: Actividades de atención de la salud humana
- R: Actividades artísticas, de entretenimiento y recreación
- S: Otras actividades de servicios

**Índices:**
- `idx_sector_codigo` on `codigo`
- `idx_sector_activo` on `activo`

---

### 6. **dato_historico**
Datos anuales de rendimiento de emprendimientos.

| Campo              | Tipo           | Restricciones        | Descripción                          |
|-------------------|----------------|----------------------|--------------------------------------|
| id                | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| emprendimiento_id | BIGINT         | FOREIGN KEY          | Emprendimiento relacionado           |
| anio              | INTEGER        | NOT NULL, CHECK      | Año del dato (> 1900, <= año actual) |
| ingresos          | DECIMAL(15,2)  | CHECK (>= 0)         | Ingresos del año en COP              |
| empleos           | INTEGER        | CHECK (>= 0)         | Empleos generados                    |
| innovaciones      | INTEGER        | CHECK (>= 0)         | Innovaciones implementadas           |
| fecha_registro    | TIMESTAMP      | DEFAULT NOW()        | Fecha de registro del dato           |
| created_at        | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación                    |
| updated_at        | TIMESTAMP      |                      | Fecha de actualización               |

**Índices:**
- `idx_dato_historico_emprendimiento` on `emprendimiento_id`
- `idx_dato_historico_anio` on `anio`
- `uk_dato_historico_emprendimiento_anio` UNIQUE on `(emprendimiento_id, anio)`

**Relaciones:**
- `FK_dato_historico_emprendimiento` → `emprendimiento(id)` ON DELETE CASCADE

---

### 7. **archivo_emprendimiento**
Archivos subidos (imágenes, documentos, videos).

| Campo              | Tipo           | Restricciones        | Descripción                          |
|-------------------|----------------|----------------------|--------------------------------------|
| id                | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| emprendimiento_id | BIGINT         | FOREIGN KEY          | Emprendimiento relacionado           |
| nombre_original   | VARCHAR(255)   | NOT NULL             | Nombre original del archivo          |
| nombre_almacenado | VARCHAR(255)   | NOT NULL, UNIQUE     | Nombre UUID en filesystem            |
| tipo_archivo      | VARCHAR(20)    | NOT NULL             | LOGO, IMAGEN, DOCUMENTO, VIDEO, OTRO |
| mime_type         | VARCHAR(100)   | NOT NULL             | Content-Type (image/jpeg, etc)       |
| tamanio           | BIGINT         | NOT NULL             | Tamaño en bytes                      |
| ruta_archivo      | VARCHAR(500)   | NOT NULL             | Ruta relativa en filesystem          |
| descripcion       | TEXT           |                      | Descripción del archivo              |
| created_at        | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación                    |
| updated_at        | TIMESTAMP      |                      | Fecha de actualización               |

**Índices:**
- `idx_archivo_emprendimiento` on `emprendimiento_id`
- `idx_archivo_nombre_almacenado` on `nombre_almacenado`

**Relaciones:**
- `FK_archivo_emprendimiento` → `emprendimiento(id)` ON DELETE CASCADE

**Límites:**
- Tamaño máximo: 10 MB
- Formatos permitidos: jpg, jpeg, png, pdf, doc, docx, mp4

---

### 8. **password_reset_token**
Tokens de reseteo de contraseña (código de 6 dígitos).

| Campo       | Tipo           | Restricciones        | Descripción                          |
|------------|----------------|----------------------|--------------------------------------|
| id         | BIGSERIAL      | PRIMARY KEY          | ID único autogenerado                |
| usuario_id | BIGINT         | FOREIGN KEY          | Usuario que solicitó el reseteo      |
| code       | VARCHAR(6)     | NOT NULL, UNIQUE     | Código de 6 dígitos                  |
| expiry_date| TIMESTAMP      | NOT NULL             | Fecha de expiración (15 minutos)     |
| used       | BOOLEAN        | DEFAULT FALSE        | Si ya fue usado                      |
| created_at | TIMESTAMP      | DEFAULT NOW()        | Fecha de creación                    |

**Índices:**
- `idx_password_reset_code` on `code`
- `idx_password_reset_usuario` on `usuario_id`

**Relaciones:**
- `FK_password_reset_usuario` → `usuario(id)` ON DELETE CASCADE

**Limpieza automática:**
- Tokens expirados se eliminan periódicamente
- Tokens usados se invalidan inmediatamente

---

## 🔍 Consultas SQL Importantes

### 1. Producción total por tipo y región
```sql
SELECT 
    r.departamento,
    r.municipio,
    te.nombre as tipo_emprendimiento,
    s.nombre as sector,
    COUNT(e.id) as total_emprendimientos,
    SUM(dh.empleos) as total_empleos,
    SUM(dh.ingresos) as ingresos_totales,
    AVG(dh.ingresos) as ingreso_promedio
FROM emprendimiento e
INNER JOIN region r ON e.region_id = r.id
INNER JOIN tipo_emprendimiento te ON e.tipo_emprendimiento_id = te.id
INNER JOIN sector s ON e.sector_id = s.id
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
WHERE e.estado = 'ACTIVO'
GROUP BY r.departamento, r.municipio, te.nombre, s.nombre
ORDER BY total_emprendimientos DESC;
```

### 2. Porcentaje de emprendimientos por región
```sql
SELECT 
    r.departamento,
    r.municipio,
    COUNT(e.id) as cantidad,
    ROUND(COUNT(e.id) * 100.0 / (SELECT COUNT(*) FROM emprendimiento), 2) as porcentaje
FROM region r
LEFT JOIN emprendimiento e ON r.id = e.region_id
GROUP BY r.departamento, r.municipio
HAVING COUNT(e.id) > 0
ORDER BY porcentaje DESC;
```

### 3. Top 10 departamentos con mayor actividad
```sql
SELECT 
    r.departamento,
    COUNT(DISTINCT e.id) as total_emprendimientos,
    COUNT(DISTINCT e.usuario_id) as emprendedores_unicos,
    SUM(dh.empleos) as empleos_generados,
    AVG(dh.ingresos) as ingreso_promedio,
    SUM(e.inversion_inicial) as inversion_total
FROM region r
INNER JOIN emprendimiento e ON r.id = e.region_id
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
WHERE e.estado = 'ACTIVO'
GROUP BY r.departamento
ORDER BY total_emprendimientos DESC
LIMIT 10;
```

### 4. Evolución temporal de un emprendimiento
```sql
SELECT 
    e.nombre as emprendimiento,
    dh.anio,
    dh.ingresos,
    dh.empleos,
    dh.innovaciones,
    LAG(dh.ingresos) OVER (ORDER BY dh.anio) as ingresos_anio_anterior,
    ROUND(
        ((dh.ingresos - LAG(dh.ingresos) OVER (ORDER BY dh.anio)) / 
         LAG(dh.ingresos) OVER (ORDER BY dh.anio)) * 100, 2
    ) as crecimiento_porcentual
FROM emprendimiento e
INNER JOIN dato_historico dh ON e.id = dh.emprendimiento_id
WHERE e.id = :emprendimientoId
ORDER BY dh.anio;
```

### 5. Dashboard global
```sql
SELECT 
    COUNT(DISTINCT e.id) as total_emprendimientos,
    COUNT(DISTINCT e.usuario_id) as total_emprendedores,
    COUNT(DISTINCT r.departamento) as departamentos_activos,
    COUNT(DISTINCT r.municipio) as municipios_activos,
    SUM(e.numero_empleados) as empleos_directos,
    SUM(e.inversion_inicial) as inversion_total,
    AVG(e.inversion_inicial) as inversion_promedio,
    SUM(dh.ingresos) as ingresos_totales,
    SUM(dh.empleos) as empleos_generados_datos_historicos,
    SUM(dh.innovaciones) as innovaciones_totales
FROM emprendimiento e
LEFT JOIN region r ON e.region_id = r.id
LEFT JOIN dato_historico dh ON e.id = dh.emprendimiento_id
WHERE e.estado = 'ACTIVO';
```

---

## 🔐 Seguridad de Datos

### Encriptación
- **Contraseñas**: BCrypt con 10 rounds (costo computacional)
- **JWT**: Firmado con HS512 (clave secreta en properties)

### Auditoría
- Campos `created_at` y `updated_at` en todas las tablas
- Trigger para `updated_at` automático:
```sql
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_emprendimiento_updated_at 
    BEFORE UPDATE ON emprendimiento 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();
```

### Integridad Referencial
- **ON DELETE CASCADE**: usuario → emprendimiento → dato_historico
- **ON DELETE RESTRICT**: tipos, regiones, sectores (no se pueden borrar si están en uso)

---

## 📈 Optimización

### Índices Implementados
- **B-Tree**: Para búsquedas por igualdad y rango
- **Composite indexes**: Para filtros combinados
- **Unique indexes**: Para constraints de unicidad

### Particionamiento (futuro)
Si el volumen de `dato_historico` crece mucho:
```sql
-- Particionar por año
CREATE TABLE dato_historico_2024 PARTITION OF dato_historico
    FOR VALUES FROM (2024) TO (2025);
```

### Materialised Views (futuro)
Para estadísticas pesadas:
```sql
CREATE MATERIALIZED VIEW mv_estadisticas_globales AS
SELECT ... 
(consulta compleja)
WITH DATA;

-- Refresh periódico
REFRESH MATERIALIZED VIEW mv_estadisticas_globales;
```

---

## 🔄 Migración de Datos

### Scripts de Inicialización
Ubicados en `docs_temp/scripts/`:

1. **01_create_tables.sql**: Creación de tablas
2. **02_create_indexes.sql**: Índices y constraints
3. **03_seed_data.sql**: Datos iniciales (tipos, sectores)
4. **scripts Python**: Carga de DIVIPOLA (1,120 municipios)

### Orden de Ejecución
```bash
# 1. Crear base de datos
createdb red_comunitaria

# 2. Ejecutar scripts SQL
psql -U postgres -d red_comunitaria -f 01_create_tables.sql
psql -U postgres -d red_comunitaria -f 02_create_indexes.sql
psql -U postgres -d red_comunitaria -f 03_seed_data.sql

# 3. Cargar DIVIPOLA
python load_divipola.py
```

---

## 📊 Tamaño Estimado

| Tabla                  | Filas Iniciales | Crecimiento Estimado    |
|-----------------------|----------------|-------------------------|
| usuario               | 100            | +50/mes                 |
| emprendimiento        | 500            | +100/mes                |
| tipo_emprendimiento   | 6              | Estable                 |
| region                | 1,120          | Estable                 |
| sector                | 19             | Estable                 |
| dato_historico        | 2,000          | +500/mes                |
| archivo_emprendimiento| 1,000          | +200/mes                |
| password_reset_token  | Variable       | Limpieza automática     |

**Tamaño total estimado (1 año):** ~500 MB - 1 GB

---

## 🔗 Diagrama de Conexión

```
┌──────────────────────────────────────────────────┐
│            Spring Boot Application               │
│                                                   │
│  ┌─────────────────────────────────────────┐    │
│  │        HikariCP Connection Pool         │    │
│  │  - maxPoolSize: 10                      │    │
│  │  - minIdle: 2                           │    │
│  │  - connectionTimeout: 30s               │    │
│  └────────────────┬────────────────────────┘    │
│                   │                              │
└───────────────────┼──────────────────────────────┘
                    │ JDBC
                    ▼
┌──────────────────────────────────────────────────┐
│               PostgreSQL Server                   │
│            jdbc:postgresql://localhost:5432       │
│                 red_comunitaria                   │
└──────────────────────────────────────────────────┘
```

---

**Desarrollador:** devpfan (pfabian@outlook.com)  
**Fecha:** Febrero 2026  
**Versión:** 1.0.0
