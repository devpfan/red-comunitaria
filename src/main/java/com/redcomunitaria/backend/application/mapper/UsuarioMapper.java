package com.redcomunitaria.backend.application.mapper;

import org.springframework.stereotype.Component;

import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.domain.model.Rol;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.RolEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

/**
 * Mapper entre Usuario (domain) y UsuarioEntity (JPA)
 */
@Component
public class UsuarioMapper {
    
    /**
     * Convierte de UsuarioEntity a Usuario (domain)
     */
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Usuario.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .telefono(entity.getTelefono())
                .fechaRegistro(entity.getFechaRegistro())
                .activo(entity.getActivo())
                .rol(toRolDomain(entity.getRol()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    /**
     * Convierte de Usuario (domain) a UsuarioEntity
     */
    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .telefono(usuario.getTelefono())
                .fechaRegistro(usuario.getFechaRegistro())
                .activo(usuario.getActivo())
                .rol(toRolEntity(usuario.getRol()))
                .createdAt(usuario.getCreatedAt())
                .updatedAt(usuario.getUpdatedAt())
                .build();
    }
    
    /**
     * Convierte de Usuario (domain) a UsuarioResponse (DTO)
     */
    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .nombreCompleto(usuario.getNombreCompleto())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .fechaRegistro(usuario.getFechaRegistro())
                .activo(usuario.getActivo())
                .rol(usuario.getRol())
                .build();
    }
    
    private Rol toRolDomain(RolEntity rolEntity) {
        if (rolEntity == null) {
            return null;
        }
        return Rol.valueOf(rolEntity.name());
    }
    
    private RolEntity toRolEntity(Rol rol) {
        if (rol == null) {
            return null;
        }
        return RolEntity.valueOf(rol.name());
    }
}
