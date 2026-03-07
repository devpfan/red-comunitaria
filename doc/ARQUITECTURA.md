# Arquitectura del Sistema - Red Comunitaria

## 📐 Patrón Arquitectónico

Este proyecto implementa **Arquitectura Hexagonal** (Ports & Adapters) propuesta por Alistair Cockburn, también conocida como **Clean Architecture**.

### Objetivo
Separar la lógica de negocio del núcleo de la aplicación de los detalles de implementación (frameworks, bases de datos, APIs externas), haciendo el sistema:
- ✅ **Independiente de frameworks**
- ✅ **Testeable** (sin necesidad de UI o BD)
- ✅ **Independiente de la UI**
- ✅ **Independiente de la BD**
- ✅ **Independiente de cualquier agente externo**

---

## 🏛️ Capas de la Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                     ADAPTADORES DE ENTRADA                       │
│         (REST Controllers, GraphQL, WebSockets, etc.)           │
│                                                                  │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐                │
│  │   Auth     │  │ Emprendim. │  │  Archivos  │                │
│  │ Controller │  │ Controller │  │ Controller │  ...            │
│  └─────┬──────┘  └─────┬──────┘  └─────┬──────┘                │
│        │               │               │                        │
└────────┼───────────────┼───────────────┼────────────────────────┘
         │               │               │
         │               ▼               │
         │     ┌─────────────────┐       │
         │     │  PUERTO IN      │       │
         └────►│   (UseCase)     │◄──────┘
               └────────┬────────┘
                        │
         ┌──────────────┴──────────────┐
         │                             │
┌────────▼─────────────────────────────▼──────────────────┐
│                  CAPA DE APLICACIÓN                      │
│         (Casos de Uso - Lógica de Orquestación)         │
│                                                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Auth       │  │ Emprendim.   │  │  Archivo     │  │
│  │ UseCaseImpl  │  │ UseCaseImpl  │  │ UseCaseImpl  │  │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘  │
│         │                 │                 │          │
└─────────┼─────────────────┼─────────────────┼──────────┘
          │                 │                 │
          │                 ▼                 │
          │        ┌─────────────────┐        │
          │        │  CAPA DOMINIO   │        │
          └───────►│  (Entidades +   │◄───────┘
                   │   Puertos OUT)  │
                   └────────┬────────┘
                            │
         ┌──────────────────┴──────────────────┐
         │                                     │
┌────────▼─────────────────────────────────────▼──────────┐
│                 ADAPTADORES DE SALIDA                    │
│     (JPA Repositories, File Storage, External APIs)     │
│                                                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Usuario     │  │ Emprendim.   │  │   File       │  │
│  │  Repo        │  │  Repo        │  │   Storage    │  │
│  │  Adapter     │  │  Adapter     │  │   Service    │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
│                                                          │
└──────────────────────────────────────────────────────────┘
                            │
                   ┌────────▼────────┐
                   │   PostgreSQL    │
                   │   Filesystem    │
                   └─────────────────┘
```

---

## 📦 Estructura de Carpetas

```
src/main/java/com/redcomunitaria/backend/
│
├── 📱 application/                      # CAPA DE APLICACIÓN
│   ├── dto/                            # Data Transfer Objects
│   │   ├── request/                    # DTOs de entrada
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegisterRequest.java
│   │   │   ├── EmprendimientoCreateRequest.java
│   │   │   └── DatoHistoricoRequest.java
│   │   └── response/                   # DTOs de salida
│   │       ├── AuthResponse.java
│   │       ├── UsuarioResponse.java
│   │       ├── EmprendimientoResponse.java
│   │       └── EstadisticaResponse.java
│   │
│   ├── mapper/                         # Mappers con MapStruct
│   │   ├── UsuarioMapper.java          # Domain ↔ Entity ↔ DTO
│   │   ├── EmprendimientoMapper.java
│   │   ├── DatoHistoricoMapper.java
│   │   └── ArchivoEmprendimientoMapper.java
│   │
│   └── usecase/                        # Implementación de casos de uso
│       ├── AuthUseCaseImpl.java        # Autenticación
│       ├── EmprendimientoUseCaseImpl.java  # CRUD emprendimientos
│       ├── EstadisticaUseCaseImpl.java # Consultas analíticas
│       └── DatoHistoricoUseCaseImpl.java
│
├── 🎯 domain/                           # CAPA DE DOMINIO (núcleo)
│   ├── model/                          # Entidades de negocio
│   │   ├── Usuario.java                # POJO con @Builder
│   │   ├── Emprendimiento.java
│   │   ├── Region.java
│   │   ├── Sector.java
│   │   ├── TipoEmprendimiento.java
│   │   ├── DatoHistorico.java
│   │   └── ArchivoEmprendimiento.java
│   │
│   ├── port/                           # Puertos (interfaces)
│   │   ├── in/                         # Puertos de entrada
│   │   │   ├── AuthUseCase.java        # Contratos de casos de uso
│   │   │   ├── EmprendimientoUseCase.java
│   │   │   ├── EstadisticaUseCase.java
│   │   │   └── UsuarioAdminUseCase.java
│   │   │
│   │   └── out/                        # Puertos de salida
│   │       ├── UsuarioRepositoryPort.java  # Contratos de persistencia
│   │       ├── EmprendimientoRepositoryPort.java
│   │       ├── RegionRepositoryPort.java
│   │       └── FileStoragePort.java
│   │
│   └── exception/                      # Excepciones de dominio
│       ├── NotFoundException.java
│       ├── DuplicateException.java
│       └── AuthenticationException.java
│
└── 🔌 infrastructure/                   # CAPA DE INFRAESTRUCTURA
    ├── adapter/
    │   ├── in/                         # Adaptadores de entrada
    │   │   └── rest/                   # REST API
    │   │       ├── AuthController.java
    │   │       ├── EmprendimientoController.java
    │   │       ├── UsuarioAdminController.java
    │   │       ├── EstadisticaController.java
    │   │       ├── DatoHistoricoController.java
    │   │       └── ArchivoEmprendimientoController.java
    │   │
    │   └── out/                        # Adaptadores de salida
    │       └── persistence/            # Persistencia JPA
    │           ├── entity/             # Entidades JPA (@Entity)
    │           │   ├── UsuarioEntity.java
    │           │   ├── EmprendimientoEntity.java
    │           │   ├── RegionEntity.java
    │           │   └── DatoHistoricoEntity.java
    │           │
    │           ├── repository/         # JPA Repositories
    │           │   ├── UsuarioJpaRepository.java
    │           │   ├── EmprendimientoJpaRepository.java
    │           │   └── RegionJpaRepository.java
    │           │
    │           ├── adapter/            # Implementación de ports OUT
    │           │   ├── UsuarioRepositoryAdapter.java
    │           │   ├── EmprendimientoRepositoryAdapter.java
    │           │   └── RegionRepositoryAdapter.java
    │           │
    │           └── specification/      # Specifications (filtros dinámicos)
    │               └── EmprendimientoSpecifications.java
    │
    ├── config/                         # Configuraciones
    │   ├── SecurityConfig.java         # Spring Security + JWT
    │   ├── CorsConfig.java             # CORS
    │   ├── BeanConfig.java             # Beans (@EnableCaching)
    │   └── OpenApiConfig.java          # Swagger/OpenAPI
    │
    ├── security/                       # Seguridad
    │   ├── jwt/
    │   │   ├── JwtTokenProvider.java
    │   │   ├── JwtAuthenticationFilter.java
    │   │   └── JwtAuthenticationEntryPoint.java
    │   └── service/
    │       └── CustomUserDetailsService.java
    │
    ├── exception/                      # Manejo global de errores
    │   └── GlobalExceptionHandler.java
    │
    ├── service/                        # Servicios de infraestructura
    │   └── FileStorageService.java
    │
    └── util/                           # Utilidades
        └── PageUtils.java              # Paginación reutilizable
```

---

## 🔄 Flujo de Datos

### Ejemplo: Crear un Emprendimiento

```
1. REQUEST (HTTP POST /api/emprendimientos)
   ↓
   [REST Controller] AuthController.create(EmprendimientoCreateRequest)
   ↓
2. VALIDACIÓN (@Valid)
   ↓
3. CONVERSIÓN DTO → Domain
   ↓
   [Use Case] EmprendimientoUseCaseImpl.create(Emprendimiento)
   ↓
4. LÓGICA DE NEGOCIO
   - Validar usuario propietario
   - Validar región existe
   - Validar sector existe
   ↓
5. LLAMADA A PUERTO OUT
   emprendimientoRepository.save(emprendimiento)
   ↓
   [Adapter OUT] EmprendimientoRepositoryAdapter.save()
   ↓
6. CONVERSIÓN Domain → Entity
   mapper.toEntity(emprendimiento)
   ↓
7. PERSISTENCIA
   jpaRepository.save(entity)
   ↓
8. PostgreSQL
   ↓
9. CONVERSIÓN Entity → Domain
   mapper.toDomain(savedEntity)
   ↓
10. RETORNO AL USE CASE
    ↓
11. CONVERSIÓN Domain → DTO
    mapper.toResponse(emprendimiento)
    ↓
12. RESPONSE (HTTP 201 + JSON)
```

---

## 🎨 Principios Aplicados

### 1. **Dependency Inversion (SOLID)**
- Los casos de uso (application) dependen de **interfaces** (ports)
- Los adaptadores implementan esas interfaces
- El dominio NO conoce detalles de infraestructura

### 2. **Single Responsibility**
- Controllers: Solo reciben requests y devuelven responses
- Use Cases: Solo lógica de orquestación
- Domain: Solo lógica de negocio
- Adapters: Solo conversión y persistencia

### 3. **Open/Closed**
- Se puede cambiar la BD (PostgreSQL → MongoDB) sin modificar el dominio
- Se puede agregar GraphQL sin tocar casos de uso

### 4. **Interface Segregation**
- Ports específicos por funcionalidad
- No interfaces gordas con muchos métodos

---

## 🔐 Seguridad

### Autenticación: JWT (JSON Web Tokens)
```
1. Usuario → POST /api/auth/login
2. Backend valida credenciales
3. Backend genera JWT firmado (HS512)
4. Cliente almacena JWT
5. Cliente envía JWT en header: Authorization: Bearer <token>
6. JwtAuthenticationFilter valida el token
7. Spring Security autoriza la request
```

### Autorización: Roles
- **USER**: CRUD de sus propios emprendimientos
- **ADMIN**: Gestión de usuarios, regiones, tipos, sectores

### Filtros de Seguridad
```
JwtAuthenticationFilter (valida token)
  ↓
Spring Security FilterChain
  ↓
@PreAuthorize("hasRole('ADMIN')") en endpoints específicos
```

---

## 🗃️ Persistencia

### ORM: JPA/Hibernate

**Ventajas de esta arquitectura:**
1. **Entities JPA** solo en `infrastructure/adapter/out/persistence/entity`
2. **Domain models** sin anotaciones JPA (POJOs puros)
3. Se puede cambiar de JPA a otro ORM sin afectar el dominio

### Mappers: MapStruct
- **8 mappers** automáticos (65% menos código)
- Conversión automática de enums
- Null-safety built-in
- Compile-time (sin reflexión)

### Cache: Spring Cache
- `@Cacheable` en tipos, regiones, sectores
- Reduce queries repetitivas
- `@CacheEvict` en writes

---

## 📊 Consultas Complejas

### JPA Specifications
Para filtros dinámicos en emprendimientos:
```java
Specification<EmprendimientoEntity> spec = 
    EmprendimientoSpecifications.withFiltros(filtros);
```

### Queries Nativas
Para estadísticas agregadas:
```java
@Query(value = "SELECT ...", nativeQuery = true)
List<EstadisticaTipoProjection> obtenerEstadisticasPorTipo();
```

---

## 📝 Documentación API

### Swagger/OpenAPI 3
- **URL**: `http://localhost:8080/swagger-ui.html`
- Todos los endpoints documentados con `@Operation`, `@ApiResponses`
- Ejemplos de request/response
- Autenticación JWT integrada

---

## 🧪 Testing (Pendiente)

### Estructura Recomendada
```
src/test/java/
├── domain/                     # Unit tests (lógica de negocio)
│   └── model/
├── application/usecase/        # Unit tests (casos de uso)
├── infrastructure/
│   ├── adapter/in/rest/        # Integration tests (controllers)
│   └── adapter/out/persistence # Integration tests (repositories)
```

---

## 🚀 Escalabilidad

### Ventajas de esta arquitectura:

1. **Microservicios**: Cada caso de uso puede ser un microservicio
2. **Event-Driven**: Se puede agregar mensajería (Kafka, RabbitMQ) sin tocar dominio
3. **CQRS**: Separar commands y queries fácilmente
4. **Multi-tenancy**: Agregar tenant por usuario sin refactoring masivo

---

## 📚 Referencias

- [Hexagonal Architecture - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MapStruct Documentation](https://mapstruct.org/)

---

**Desarrollador:** devpfan (pfabian@outlook.com)  
**Fecha:** Febrero 2026  
**Versión:** 1.0.0
