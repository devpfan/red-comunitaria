package com.redcomunitaria.backend.application.usecase;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.redcomunitaria.backend.domain.exception.NotFoundException;
import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.TipoArchivo;
import com.redcomunitaria.backend.domain.port.in.ArchivoEmprendimientoUseCase;
import com.redcomunitaria.backend.domain.port.out.ArchivoEmprendimientoRepositoryPort;
import com.redcomunitaria.backend.domain.port.out.EmprendimientoRepositoryPort;
import com.redcomunitaria.backend.infrastructure.exception.FileStorageException;
import com.redcomunitaria.backend.infrastructure.service.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación: Casos de uso de archivos de emprendimiento
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArchivoEmprendimientoUseCaseImpl implements ArchivoEmprendimientoUseCase {
    
    private final ArchivoEmprendimientoRepositoryPort archivoRepository;
    private final EmprendimientoRepositoryPort emprendimientoRepository;
    private final FileStorageService fileStorageService;
    
    @Override
    @Transactional
    public ArchivoEmprendimiento uploadFile(Long emprendimientoId, MultipartFile file, 
                                           TipoArchivo tipoArchivo, String descripcion, Long usuarioId) {
        
        log.info("Subiendo archivo para emprendimiento: {}", emprendimientoId);
        
        // Verificar que el emprendimiento existe
        Emprendimiento emprendimiento = emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        // Verificar que el usuario es el propietario
        if (!emprendimiento.getUsuario().getId().equals(usuarioId)) {
            throw new FileStorageException("No tienes permisos para subir archivos a este emprendimiento");
        }
        
        // Determinar tipo de archivo si no se especificó
        if (tipoArchivo == null) {
            tipoArchivo = fileStorageService.determineTipoArchivo(file.getContentType(), file.getOriginalFilename());
        }
        
        // Almacenar archivo físicamente
        String rutaArchivo = fileStorageService.storeFile(file, emprendimientoId, tipoArchivo);
        
        // Crear registro en base de datos
        ArchivoEmprendimiento archivo = ArchivoEmprendimiento.builder()
                .emprendimientoId(emprendimientoId)
                .nombreOriginal(file.getOriginalFilename())
                .nombreAlmacenado(rutaArchivo.substring(rutaArchivo.lastIndexOf("/") + 1))
                .tipoArchivo(tipoArchivo)
                .mimeType(file.getContentType())
                .tamanio(file.getSize())
                .rutaArchivo(rutaArchivo)
                .descripcion(descripcion)
                .build();
        
        ArchivoEmprendimiento saved = archivoRepository.save(archivo);
        log.info("Archivo guardado con ID: {}", saved.getId());
        
        return saved;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ArchivoEmprendimiento> getFilesByEmprendimiento(Long emprendimientoId) {
        log.info("Obteniendo archivos del emprendimiento: {}", emprendimientoId);
        
        // Verificar que el emprendimiento existe
        emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        return archivoRepository.findByEmprendimientoId(emprendimientoId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Resource downloadFile(Long emprendimientoId, Long archivoId, Long usuarioId) {
        log.info("Descargando archivo {} del emprendimiento: {}", archivoId, emprendimientoId);
        
        // Buscar archivo
        ArchivoEmprendimiento archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new NotFoundException("Archivo", "id", archivoId.toString()));
        
        // Verificar que pertenece al emprendimiento
        if (!archivo.getEmprendimientoId().equals(emprendimientoId)) {
            throw new FileStorageException("El archivo no pertenece a este emprendimiento");
        }
        
        // Cargar recurso
        return fileStorageService.loadFileAsResource(archivo.getRutaArchivo());
    }
    
    @Override
    @Transactional
    public void deleteFile(Long emprendimientoId, Long archivoId, Long usuarioId) {
        log.info("Eliminando archivo {} del emprendimiento: {}", archivoId, emprendimientoId);
        
        // Buscar archivo
        ArchivoEmprendimiento archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new NotFoundException("Archivo", "id", archivoId.toString()));
        
        // Verificar que pertenece al emprendimiento
        if (!archivo.getEmprendimientoId().equals(emprendimientoId)) {
            throw new FileStorageException("El archivo no pertenece a este emprendimiento");
        }
        
        // Verificar permisos
        Emprendimiento emprendimiento = emprendimientoRepository.findById(emprendimientoId)
                .orElseThrow(() -> new NotFoundException("Emprendimiento", "id", emprendimientoId.toString()));
        
        if (!emprendimiento.getUsuario().getId().equals(usuarioId)) {
            throw new FileStorageException("No tienes permisos para eliminar archivos de este emprendimiento");
        }
        
        // Eliminar archivo físico
        fileStorageService.deleteFile(archivo.getRutaArchivo());
        
        // Eliminar registro de base de datos
        archivoRepository.deleteById(archivoId);
        
        log.info("Archivo eliminado exitosamente: {}", archivoId);
    }
}
