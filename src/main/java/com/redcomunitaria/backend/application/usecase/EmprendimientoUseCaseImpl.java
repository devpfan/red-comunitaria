package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.EmprendimientoUseCase;
import com.redcomunitaria.backend.domain.port.out.EmprendimientoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.RegionRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.TipoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Implementación: Casos de uso de Emprendimientos
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmprendimientoUseCaseImpl implements EmprendimientoUseCase {
    
    private final EmprendimientoRepositoryPort emprendimientoRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final RegionRepositoryPort regionRepository;
    private final TipoEmprendimientoRepositoryPort tipoRepository;
    
    @Override
    public Emprendimiento create(Emprendimiento emprendimiento) {
        Emprendimiento resolved = resolveReferences(emprendimiento);
        return emprendimientoRepository.save(resolved);
    }
    
    @Override
    public Emprendimiento update(Long id, Emprendimiento emprendimiento) {
        Emprendimiento existing = getById(id);
        Emprendimiento resolved = resolveReferences(emprendimiento);
        resolved.setId(existing.getId());
        return emprendimientoRepository.save(resolved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Emprendimiento getById(Long id) {
        return emprendimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Emprendimiento no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Emprendimiento> getAll() {
        return emprendimientoRepository.findAll();
    }
    
    @Override
    public void delete(Long id) {
        Emprendimiento existing = getById(id);
        emprendimientoRepository.deleteById(existing.getId());
    }
    
    private Emprendimiento resolveReferences(Emprendimiento emprendimiento) {
        Usuario usuario = usuarioRepository.findById(emprendimiento.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("Usuario", "id", emprendimiento.getUsuario().getId().toString()));
        
        Region region = regionRepository.findById(emprendimiento.getRegion().getId())
                .orElseThrow(() -> new NotFoundException("Región", "id", emprendimiento.getRegion().getId().toString()));
        
        TipoEmprendimiento tipo = tipoRepository.findById(emprendimiento.getTipoEmprendimiento().getId())
                .orElseThrow(() -> new NotFoundException("TipoEmprendimiento", "id", emprendimiento.getTipoEmprendimiento().getId().toString()));
        
        emprendimiento.setUsuario(usuario);
        emprendimiento.setRegion(region);
        emprendimiento.setTipoEmprendimiento(tipo);
        return emprendimiento;
    }
}
