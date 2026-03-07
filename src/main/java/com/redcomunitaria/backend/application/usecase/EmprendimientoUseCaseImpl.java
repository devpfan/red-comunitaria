package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoFiltros;
import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.EmprendimientoUseCase;
import com.redcomunitaria.backend.domain.port.out.EmprendimientoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.RegionRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.TipoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity.SectorEntity;
import com.redcomunitaria.backend.infrastructure.adapter.out.persistence.repository.SectorJpaRepository;

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
    private final SectorJpaRepository sectorRepository;
    
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
    @Transactional(readOnly = true)
    public Page<Emprendimiento> getAll(Pageable pageable) {
        return emprendimientoRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Emprendimiento> buscar(EmprendimientoFiltros filtros, Pageable pageable) {
        return emprendimientoRepository.findWithFiltros(filtros, pageable);
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
        
        SectorEntity sectorEntity = sectorRepository.findById(emprendimiento.getSector().getId())
                .orElseThrow(() -> new NotFoundException("Sector", "id", emprendimiento.getSector().getId().toString()));
        
        Sector sector = Sector.builder()
                .id(sectorEntity.getId())
                .codigo(sectorEntity.getCodigo())
                .nombre(sectorEntity.getNombre())
                .descripcion(sectorEntity.getDescripcion())
                .activo(sectorEntity.getActivo())
                .build();
        
        emprendimiento.setUsuario(usuario);
        emprendimiento.setRegion(region);
        emprendimiento.setTipoEmprendimiento(tipo);
        emprendimiento.setSector(sector);
        return emprendimiento;
    }
}
