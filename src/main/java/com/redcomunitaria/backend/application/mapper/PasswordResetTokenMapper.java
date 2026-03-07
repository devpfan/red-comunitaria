package com.redcomunitaria.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.redcomunitaria.backend.domain.model.PasswordResetToken;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.PasswordResetTokenEntity;

/**
 * Mapper entre PasswordResetToken (domain) y PasswordResetTokenEntity (JPA)
 * Maneja la relación anidada con Usuario
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PasswordResetTokenMapper {
    
    /**
     * Convierte de PasswordResetTokenEntity a PasswordResetToken (domain)
     * Mapea id, email, nombre del Usuario desde la entidad
     */
    @Mapping(target = "usuario.id", source = "usuario.id")
    @Mapping(target = "usuario.email", source = "usuario.email")
    @Mapping(target = "usuario.nombre", source = "usuario.nombre")
    @Mapping(target = "usuario.apellido", ignore = true)
    @Mapping(target = "usuario.password", ignore = true)
    @Mapping(target = "usuario.telefono", ignore = true)
    @Mapping(target = "usuario.fechaRegistro", ignore = true)
    @Mapping(target = "usuario.activo", ignore = true)
    @Mapping(target = "usuario.rol", ignore = true)
    @Mapping(target = "usuario.createdAt", ignore = true)
    @Mapping(target = "usuario.updatedAt", ignore = true)
    PasswordResetToken toDomain(PasswordResetTokenEntity entity);
    
    /**
     * Convierte de PasswordResetToken (domain) a PasswordResetTokenEntity
     * Solo mapea el ID del Usuario (referencia mínima)
     */
    @Mapping(target = "usuario.id", source = "usuario.id")
    @Mapping(target = "usuario.nombre", ignore = true)
    @Mapping(target = "usuario.apellido", ignore = true)
    @Mapping(target = "usuario.email", ignore = true)
    @Mapping(target = "usuario.password", ignore = true)
    @Mapping(target = "usuario.telefono", ignore = true)
    @Mapping(target = "usuario.fechaRegistro", ignore = true)
    @Mapping(target = "usuario.activo", ignore = true)
    @Mapping(target = "usuario.rol", ignore = true)
    @Mapping(target = "usuario.createdAt", ignore = true)
    @Mapping(target = "usuario.updatedAt", ignore = true)
    PasswordResetTokenEntity toEntity(PasswordResetToken token);
}
