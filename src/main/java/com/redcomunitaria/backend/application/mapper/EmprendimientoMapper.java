package com.redcomunitaria.backend.application.mapper;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.dto.response.EmprendimientoResponse;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.EstadoEmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RegionEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.TipoEmprendimientoEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

/**
 * Mapper entre Emprendimiento (domain) y EmprendimientoEntity (JPA)
 */
@Component
public class EmprendimientoMapper {
    
    public Emprendimiento toDomain(EmprendimientoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Usuario usuario = Usuario.builder()
                .id(entity.getUsuario().getId())
                .nombre(entity.getUsuario().getNombre())
                .apellido(entity.getUsuario().getApellido())
                .email(entity.getUsuario().getEmail())
                .build();
        
        TipoEmprendimiento tipo = TipoEmprendimiento.builder()
                .id(entity.getTipoEmprendimiento().getId())
                .nombre(entity.getTipoEmprendimiento().getNombre())
                .build();
        
        Region region = Region.builder()
                .id(entity.getRegion().getId())
                .departamento(entity.getRegion().getDepartamento())
                .municipio(entity.getRegion().getMunicipio())
                .build();
        
        return Emprendimiento.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .fechaCreacion(entity.getFechaCreacion())
                .sector(entity.getSector())
                .numeroEmpleados(entity.getNumeroEmpleados())
                .inversionInicial(entity.getInversionInicial())
                .estado(toEstadoDomain(entity.getEstado()))
                .usuario(usuario)
                .tipoEmprendimiento(tipo)
                .region(region)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public EmprendimientoEntity toEntity(Emprendimiento emprendimiento) {
        if (emprendimiento == null) {
            return null;
        }
        
        UsuarioEntity usuario = UsuarioEntity.builder()
                .id(emprendimiento.getUsuario().getId())
                .build();
        
        TipoEmprendimientoEntity tipo = TipoEmprendimientoEntity.builder()
                .id(emprendimiento.getTipoEmprendimiento().getId())
                .build();
        
        RegionEntity region = RegionEntity.builder()
                .id(emprendimiento.getRegion().getId())
                .build();
        
        return EmprendimientoEntity.builder()
                .id(emprendimiento.getId())
                .nombre(emprendimiento.getNombre())
                .descripcion(emprendimiento.getDescripcion())
                .fechaCreacion(emprendimiento.getFechaCreacion())
                .sector(emprendimiento.getSector())
                .numeroEmpleados(emprendimiento.getNumeroEmpleados())
                .inversionInicial(emprendimiento.getInversionInicial())
                .estado(toEstadoEntity(emprendimiento.getEstado()))
                .usuario(usuario)
                .tipoEmprendimiento(tipo)
                .region(region)
                .createdAt(emprendimiento.getCreatedAt())
                .updatedAt(emprendimiento.getUpdatedAt())
                .build();
    }
    
    public EmprendimientoResponse toResponse(Emprendimiento emprendimiento) {
        if (emprendimiento == null) {
            return null;
        }
        
        String usuarioNombre = emprendimiento.getUsuario() != null
                ? emprendimiento.getUsuario().getNombreCompleto()
                : null;
        
        String tipoNombre = emprendimiento.getTipoEmprendimiento() != null
                ? emprendimiento.getTipoEmprendimiento().getNombre()
                : null;
        
        String regionDepartamento = emprendimiento.getRegion() != null
                ? emprendimiento.getRegion().getDepartamento()
                : null;
        
        String regionMunicipio = emprendimiento.getRegion() != null
                ? emprendimiento.getRegion().getMunicipio()
                : null;
        
        return EmprendimientoResponse.builder()
                .id(emprendimiento.getId())
                .nombre(emprendimiento.getNombre())
                .descripcion(emprendimiento.getDescripcion())
                .fechaCreacion(emprendimiento.getFechaCreacion())
                .sector(emprendimiento.getSector())
                .numeroEmpleados(emprendimiento.getNumeroEmpleados())
                .inversionInicial(emprendimiento.getInversionInicial())
                .estado(emprendimiento.getEstado())
                .usuarioId(emprendimiento.getUsuario() != null ? emprendimiento.getUsuario().getId() : null)
                .usuarioNombre(usuarioNombre)
                .tipoEmprendimientoId(emprendimiento.getTipoEmprendimiento() != null ? emprendimiento.getTipoEmprendimiento().getId() : null)
                .tipoEmprendimientoNombre(tipoNombre)
                .regionId(emprendimiento.getRegion() != null ? emprendimiento.getRegion().getId() : null)
                .regionDepartamento(regionDepartamento)
                .regionMunicipio(regionMunicipio)
                .build();
    }
    
    private EstadoEmprendimiento toEstadoDomain(EstadoEmprendimientoEntity entity) {
        if (entity == null) {
            return null;
        }
        return EstadoEmprendimiento.valueOf(entity.name());
    }
    
    private EstadoEmprendimientoEntity toEstadoEntity(EstadoEmprendimiento estado) {
        if (estado == null) {
            return null;
        }
        return EstadoEmprendimientoEntity.valueOf(estado.name());
    }
}
