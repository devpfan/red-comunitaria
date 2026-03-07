package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

/**
 * Mapper entre Usuario (domain) y UsuarioEntity (JPA)
 * MapStruct mapea automáticamente enums con el mismo nombre
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {
    
    /**
     * Convierte de UsuarioEntity a Usuario (domain)
     */
    Usuario toDomain(UsuarioEntity entity);
    
    /**
     * Convierte de Usuario (domain) a UsuarioEntity
     */
    UsuarioEntity toEntity(Usuario usuario);
    
    /**
     * Convierte de Usuario (domain) a UsuarioResponse (DTO)
     * nombreCompleto se calcula en el getter del dominio
     */
    @Mapping(target = "nombreCompleto", expression = "java(usuario.getNombreCompleto())")
    UsuarioResponse toResponse(Usuario usuario);
}
