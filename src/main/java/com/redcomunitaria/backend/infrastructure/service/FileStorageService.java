package com.redcomunitaria.backend.infrastructure.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.redcomunitaria.backend.domain.model.TipoArchivo;
import com.redcomunitaria.backend.infrastructure.exception.FileStorageException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio: Almacenamiento local de archivos
 */
@Service
@Slf4j
public class FileStorageService {
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    @Value("${file.max-size:10485760}") // 10MB por defecto
    private long maxFileSize;
    
    private Path fileStorageLocation;
    
    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("Directorio de almacenamiento creado: {}", this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo crear el directorio de almacenamiento", ex);
        }
    }
    
    /**
     * Almacena un archivo y retorna el nombre generado
     */
    public String storeFile(MultipartFile file, Long emprendimientoId, TipoArchivo tipoArchivo) {
        // Validar archivo
        validateFile(file);
        
        // Limpiar nombre original
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        
        // Generar nombre único
        String storedFileName = generateUniqueFileName(emprendimientoId, tipoArchivo, fileExtension);
        
        try {
            // Crear subdirectorio para el emprendimiento
            Path emprendimientoDir = this.fileStorageLocation.resolve(String.valueOf(emprendimientoId));
            Files.createDirectories(emprendimientoDir);
            
            // Crear subdirectorio por tipo
            Path tipoDir = emprendimientoDir.resolve(tipoArchivo.name().toLowerCase());
            Files.createDirectories(tipoDir);
            
            // Guardar archivo
            Path targetLocation = tipoDir.resolve(storedFileName);
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            
            log.info("Archivo almacenado: {}", targetLocation);
            
            // Retornar ruta relativa
            return emprendimientoId + "/" + tipoArchivo.name().toLowerCase() + "/" + storedFileName;
            
        } catch (IOException ex) {
            throw new FileStorageException("No se pudo almacenar el archivo " + originalFilename, ex);
        }
    }
    
    /**
     * Carga un archivo como Resource
     */
    public Resource loadFileAsResource(String rutaArchivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(rutaArchivo).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Archivo no encontrado: " + rutaArchivo);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("Archivo no encontrado: " + rutaArchivo, ex);
        }
    }
    
    /**
     * Elimina un archivo físicamente
     */
    public void deleteFile(String rutaArchivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(rutaArchivo).normalize();
            Files.deleteIfExists(filePath);
            log.info("Archivo eliminado: {}", filePath);
        } catch (IOException ex) {
            log.error("Error al eliminar archivo: {}", rutaArchivo, ex);
            throw new FileStorageException("No se pudo eliminar el archivo: " + rutaArchivo, ex);
        }
    }
    
    /**
     * Valida el archivo (tamaño, tipo, extensión)
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("El archivo está vacío");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new FileStorageException("El archivo excede el tamaño máximo permitido de " + (maxFileSize / 1024 / 1024) + "MB");
        }
        
        String filename = file.getOriginalFilename();
        if (filename == null || filename.contains("..")) {
            throw new FileStorageException("Nombre de archivo inválido: " + filename);
        }
        
        // Validar extensión
        String extension = getFileExtension(filename);
        if (!isAllowedExtension(extension)) {
            throw new FileStorageException("Extensión de archivo no permitida: " + extension);
        }
    }
    
    /**
     * Genera un nombre único para el archivo
     */
    private String generateUniqueFileName(Long emprendimientoId, TipoArchivo tipoArchivo, String extension) {
        return UUID.randomUUID().toString() + extension;
    }
    
    /**
     * Obtiene la extensión del archivo
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
    
    /**
     * Verifica si la extensión es permitida
     */
    private boolean isAllowedExtension(String extension) {
        String[] allowedExtensions = {
            ".jpg", ".jpeg", ".png", ".gif", ".webp", // Imágenes
            ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".txt", // Documentos
            ".mp4", ".avi", ".mov", ".wmv" // Videos
        };
        
        String lowerExtension = extension.toLowerCase();
        for (String allowed : allowedExtensions) {
            if (allowed.equals(lowerExtension)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determina el tipo de archivo basado en MIME type
     */
    public TipoArchivo determineTipoArchivo(String mimeType, String filename) {
        if (mimeType == null) {
            return TipoArchivo.OTRO;
        }
        
        String lowerFilename = filename != null ? filename.toLowerCase() : "";
        
        if (lowerFilename.contains("logo")) {
            return TipoArchivo.LOGO;
        }
        
        if (mimeType.startsWith("image/")) {
            return TipoArchivo.IMAGEN;
        }
        
        if (mimeType.startsWith("video/")) {
            return TipoArchivo.VIDEO;
        }
        
        if (mimeType.contains("pdf") || mimeType.contains("document") || 
            mimeType.contains("spreadsheet") || mimeType.contains("text")) {
            return TipoArchivo.DOCUMENTO;
        }
        
        return TipoArchivo.OTRO;
    }
}
