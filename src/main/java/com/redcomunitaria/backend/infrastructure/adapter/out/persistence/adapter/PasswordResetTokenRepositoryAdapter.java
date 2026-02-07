package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.model.PasswordResetToken;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.out.PasswordResetTokenRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.PasswordResetTokenEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.PasswordResetTokenJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador: Implementación del repositorio de tokens de reseteo
 */
@Component
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepositoryPort {
    
    private final PasswordResetTokenJpaRepository jpaRepository;
    
    @Override
    @Transactional
    public PasswordResetToken save(PasswordResetToken token) {
        PasswordResetTokenEntity entity = toEntity(token);
        PasswordResetTokenEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PasswordResetToken> findByCodeAndNotUsed(String code) {
        return jpaRepository.findByCodeAndUsed(code, false)
                .map(this::toDomain);
    }
    
    @Override
    @Transactional
    public void invalidateUserTokens(Long usuarioId) {
        jpaRepository.invalidateUserTokens(usuarioId);
    }
    
    @Override
    @Transactional
    public void deleteExpiredTokens() {
        jpaRepository.deleteExpiredTokens(LocalDateTime.now());
    }
    
    private PasswordResetTokenEntity toEntity(PasswordResetToken token) {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .id(token.getUsuario().getId())
                .build();
        
        return PasswordResetTokenEntity.builder()
                .id(token.getId())
                .code(token.getCode())
                .usuario(usuarioEntity)
                .expiryDate(token.getExpiryDate())
                .used(token.isUsed())
                .createdAt(token.getCreatedAt())
                .build();
    }
    
    private PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        Usuario usuario = Usuario.builder()
                .id(entity.getUsuario().getId())
                .email(entity.getUsuario().getEmail())
                .nombre(entity.getUsuario().getNombre())
                .build();
        
        return PasswordResetToken.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .usuario(usuario)
                .expiryDate(entity.getExpiryDate())
                .used(entity.isUsed())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
