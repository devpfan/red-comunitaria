package com.redcomunitaria.backend.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redcomunitaria.backend.application.dto.request.DatoHistoricoRequest;
import com.redcomunitaria.backend.domain.exception.DuplicateException;
import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.DatoHistorico;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.port.in.DatoHistoricoUseCase;
import com.redcomunitaria.backend.domain.port.out.DatoHistoricoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.EmprendimientoRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación: Casos de uso de datos históricos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DatoHistoricoUseCaseImpl implements DatoHistoricoUseCase {
    
    private final DatoHistoricoRepositoryPort datoHistoricoRepository;
    private final EmprendimientoRepositoryPort emprendimientoRepository;
    
    @Override
    @Transactional
    public DatoHistorico create(Long emprendimientoId, DatoHistoricoRequest request, Long usuarioId) {
        log.info("Creando dato histórico para emprendimiento: {} año: {}", emprendimientoId, request.getAnio());
        
        // Verificar que el emprendimiento existe y pertenece al usuario
        Emprendimiento emprendimiento = emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        if (!emprendimiento.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permisos para agregar datos a este emprendimiento");
        }
        
        // Verificar que no exista ya un registro para ese año
        if (datoHistoricoRepository.existsByEmprendimientoIdAndAnio(emprendimientoId, request.getAnio())) {
            throw new DuplicateException("Ya existe un registro histórico para el año " + request.getAnio());
        }
        
        // Crear dato histórico
        DatoHistorico datoHistorico = DatoHistorico.builder()
                .emprendimientoId(emprendimientoId)
                .anio(request.getAnio())
                .ingresos(request.getIngresos())
                .empleos(request.getEmpleos())
                .innovaciones(request.getInnovaciones())
                .fechaRegistro(LocalDateTime.now())
                .build();
        
        DatoHistorico saved = datoHistoricoRepository.save(datoHistorico);
        log.info("Dato histórico creado con ID: {}", saved.getId());
        
        return saved;
    }
    
    @Override
    @Transactional
    public DatoHistorico update(Long emprendimientoId, Integer anio, DatoHistoricoRequest request, Long usuarioId) {
        log.info("Actualizando dato histórico emprendimiento: {} año: {}", emprendimientoId, anio);
        
        // Verificar permisos
        Emprendimiento emprendimiento = emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        if (!emprendimiento.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permisos para actualizar datos de este emprendimiento");
        }
        
        // Buscar dato histórico existente
        DatoHistorico existing = datoHistoricoRepository.findByEmprendimientoIdAndAnio(emprendimientoId, anio)
                .orElseThrow(() -> new NotFoundException("Dato histórico", "año", anio.toString()));
        
        // Si se cambió el año, verificar que no exista duplicado
        if (!existing.getAnio().equals(request.getAnio())) {
            if (datoHistoricoRepository.existsByEmprendimientoIdAndAnio(emprendimientoId, request.getAnio())) {
                throw new DuplicateException("Ya existe un registro histórico para el año " + request.getAnio());
            }
        }
        
        // Actualizar campos
        existing.setAnio(request.getAnio());
        existing.setIngresos(request.getIngresos());
        existing.setEmpleos(request.getEmpleos());
        existing.setInnovaciones(request.getInnovaciones());
        
        DatoHistorico updated = datoHistoricoRepository.save(existing);
        log.info("Dato histórico actualizado: {}", updated.getId());
        
        return updated;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DatoHistorico> getByEmprendimiento(Long emprendimientoId) {
        log.info("Obteniendo datos históricos del emprendimiento: {}", emprendimientoId);
        
        // Verificar que el emprendimiento existe
        emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        return datoHistoricoRepository.findByEmprendimientoId(emprendimientoId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DatoHistorico getByEmprendimientoAndAnio(Long emprendimientoId, Integer anio) {
        log.info("Obteniendo dato histórico emprendimiento: {} año: {}", emprendimientoId, anio);
        
        return datoHistoricoRepository.findByEmprendimientoIdAndAnio(emprendimientoId, anio)
                .orElseThrow(() -> new NotFoundException("Dato histórico", "año", anio.toString()));
    }
    
    @Override
    @Transactional
    public void delete(Long emprendimientoId, Integer anio, Long usuarioId) {
        log.info("Eliminando dato histórico emprendimiento: {} año: {}", emprendimientoId, anio);
        
        // Verificar permisos
        Emprendimiento emprendimiento = emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        if (!emprendimiento.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No tienes permisos para eliminar datos de este emprendimiento");
        }
        
        // Buscar y eliminar
        DatoHistorico datoHistorico = datoHistoricoRepository.findByEmprendimientoIdAndAnio(emprendimientoId, anio)
                .orElseThrow(() -> new NotFoundException("Dato histórico", "año", anio.toString()));
        
        datoHistoricoRepository.deleteById(datoHistorico.getId());
        log.info("Dato histórico eliminado: {}", datoHistorico.getId());
    }
}
