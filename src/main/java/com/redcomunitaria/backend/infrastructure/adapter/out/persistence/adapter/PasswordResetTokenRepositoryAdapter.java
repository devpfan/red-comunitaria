package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.adapter;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.mapper.PasswordResetTokenMapper;
import com.redcomunitaria.backend.domain.model.PasswordResetToken;
import com.redcomunitaria.backend.domain.port.out.PasswordResetTokenRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.PasswordResetTokenJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador: Implementación del repositorio de tokens de reseteo
 */
@Component
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepositoryPort {
    
    private final PasswordResetTokenJpaRepository jpaRepository;
    private final PasswordResetTokenMapper mapper;
    
    @Override
    @Transactional
    public PasswordResetToken save(PasswordResetToken token) {
        var entity = mapper.toEntity(token);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PasswordResetToken> findByCodeAndNotUsed(String code) {
        return jpaRepository.findByCodeAndUsed(code, false)
                .map(mapper::toDomain);
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
}
