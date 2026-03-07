package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.redcomunitaria.backend.application.dto.response.ArchivoEmprendimientoResponse;
import com.redcomunitaria.backend.application.mapper.ArchivoEmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.ArchivoEmprendimiento;
import com.redcomunitaria.backend.domain.model.TipoArchivo;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.ArchivoEmprendimientoUseCase;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller: Archivos de emprendimiento
 */
@RestController
@RequestMapping("/api/emprendimientos/{emprendimientoId}/archivos")
@RequiredArgsConstructor
@Tag(name = "Archivos", description = "Gestión de archivos de emprendimientos")
@SecurityRequirement(name = "bearerAuth")
public class ArchivoEmprendimientoController {
    
    private final ArchivoEmprendimientoUseCase archivoUseCase;
    private final UsuarioRepositoryPort usuarioRepository;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir archivo", 
               description = "Sube un archivo (imagen, documento, video) al emprendimiento. Máximo 10MB")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Archivo subido exitosamente"),
        @ApiResponse(responseCode = "400", description = "Archivo inválido o excede tamaño"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado")
    })
    public ResponseEntity<ArchivoEmprendimientoResponse> uploadFile(
            @PathVariable Long emprendimientoId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) TipoArchivo tipoArchivo,
            @RequestParam(required = false) String descripcion,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        ArchivoEmprendimiento archivo = archivoUseCase.uploadFile(
                emprendimientoId, file, tipoArchivo, descripcion, usuario.getId());
        
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        ArchivoEmprendimientoResponse response = ArchivoEmprendimientoMapper.toResponse(archivo, baseUrl);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Listar archivos", 
               description = "Obtiene todos los archivos de un emprendimiento con sus URLs de descarga")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado")
    })
    public ResponseEntity<List<ArchivoEmprendimientoResponse>> getFiles(
            @PathVariable Long emprendimientoId) {
        
        List<ArchivoEmprendimiento> archivos = archivoUseCase.getFilesByEmprendimiento(emprendimientoId);
        
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        List<ArchivoEmprendimientoResponse> response = archivos.stream()
                .map(archivo -> ArchivoEmprendimientoMapper.toResponse(archivo, baseUrl))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{archivoId}")
    @Operation(summary = "Descargar archivo", description = "Descarga un archivo específico")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long emprendimientoId,
            @PathVariable Long archivoId,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Resource resource = archivoUseCase.downloadFile(emprendimientoId, archivoId, usuario.getId());
        
        // Determinar content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            contentType = "application/octet-stream";
        }
        
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @DeleteMapping("/{archivoId}")
    @Operation(summary = "Eliminar archivo", description = "Elimina un archivo del emprendimiento")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long emprendimientoId,
            @PathVariable Long archivoId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        archivoUseCase.deleteFile(emprendimientoId, archivoId, usuario.getId());
        
        return ResponseEntity.noContent().build();
    }
}
