package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.redcomunitaria.backend.application.dto.response.EmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.SectorEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

/**
 * Mapper entre Emprendimiento (domain) y EmprendimientoEntity (JPA)
 * Usa métodos helper para convertir relaciones a referencias con solo ID
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, 
        uses = {RegionMapper.class, TipoEmprendimientoMapper.class, SectorMapper.class})
public interface EmprendimientoMapper {
    
    /**
     * Convierte de EmprendimientoEntity a Emprendimiento (domain)
     * MapStruct mapea automáticamente los objetos anidados completos
     */
    Emprendimiento toDomain(EmprendimientoEntity entity);
    
    /**
     * Convierte de Emprendimiento (domain) a EmprendimientoEntity
     * Usa métodos @Named para construir solo referencias con ID
     */
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioToReference")
    @Mapping(target = "tipoEmprendimiento", source = "tipoEmprendimiento", qualifiedByName = "tipoToReference")
    @Mapping(target = "region", source = "region", qualifiedByName = "regionToReference")
    @Mapping(target = "sector", source = "sector", qualifiedByName = "sectorToReference")
    EmprendimientoEntity toEntity(Emprendimiento emprendimiento);
    
    /**
     * Convierte de Emprendimiento (domain) a EmprendimientoResponse (DTO)
     * Aplana los objetos anidados a campos individuales en el response
     */
    @Mapping(target = "sectorId", source = "sector.id")
    @Mapping(target = "sectorCodigo", source = "sector.codigo")
    @Mapping(target = "sectorNombre", source = "sector.nombre")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioNombre", expression = "java(emprendimiento.getUsuario() != null ? emprendimiento.getUsuario().getNombreCompleto() : null)")
    @Mapping(target = "tipoEmprendimientoId", source = "tipoEmprendimiento.id")
    @Mapping(target = "tipoEmprendimientoNombre", source = "tipoEmprendimiento.nombre")
    @Mapping(target = "regionId", source = "region.id")
    @Mapping(target = "regionDepartamento", source = "region.departamento")
    @Mapping(target = "regionMunicipio", source = "region.municipio")
    EmprendimientoResponse toResponse(Emprendimiento emprendimiento);
    
    // Métodos helper para crear referencias con solo ID
    
    @Named("usuarioToReference")
    default UsuarioEntity usuarioToReference(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioEntity.builder().id(usuario.getId()).build();
    }
    
    @Named("tipoToReference")
    default TipoEmprendimientoEntity tipoToReference(TipoEmprendimiento tipo) {
        if (tipo == null) return null;
        return TipoEmprendimientoEntity.builder().id(tipo.getId()).build();
    }
    
    @Named("regionToReference")
    default RegionEntity regionToReference(Region region) {
        if (region == null) return null;
        return RegionEntity.builder().id(region.getId()).build();
    }
    
    @Named("sectorToReference")
    default SectorEntity sectorToReference(Sector sector) {
        if (sector == null) return null;
        return SectorEntity.builder().id(sector.getId()).build();
    }
}
