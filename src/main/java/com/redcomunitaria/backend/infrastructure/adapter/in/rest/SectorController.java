package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.SectorRequest;
import com.redcomunitaria.backend.application.dto.response.SectorResponse;
import com.redcomunitaria.backend.application.mapper.SectorMapper;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.port.in.SectorUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST: Sectores
 */
@RestController
@RequestMapping("/api/sectores")
@RequiredArgsConstructor
@Tag(name = "Sectores", description = "Endpoints para gestión de sectores económicos")
@SecurityRequirement(name = "bearerAuth")
public class SectorController {
    
    private final SectorUseCase sectorUseCase;
    private final SectorMapper sectorMapper;
    
    @GetMapping
    @Operation(summary = "Listar sectores económicos activos",
               description = "Retorna el catálogo de sectores económicos activos según CIIU Colombia")
    public ResponseEntity<List<SectorResponse>> getAll() {
        var response = sectorUseCase.getAllActivos().stream()
                .map(sectorMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener sector por ID")
    public ResponseEntity<SectorResponse> getById(@PathVariable Long id) {
        var sector = sectorUseCase.getById(id);
        return ResponseEntity.ok(sectorMapper.toResponse(sector));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear sector económico (solo ADMIN)",
               description = "Crea un nuevo sector económico según CIIU. Requiere rol ADMIN.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sector creado exitosamente",
                     content = @Content(schema = @Schema(implementation = SectorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos de ADMIN", content = @Content)
    })
    public ResponseEntity<SectorResponse> create(@Valid @RequestBody SectorRequest request) {
        Sector sector = mapRequestToDomain(request);
        Sector saved = sectorUseCase.create(sector);
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorMapper.toResponse(saved));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar sector económico (solo ADMIN)")
    public ResponseEntity<SectorResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SectorRequest request) {
        Sector sector = mapRequestToDomain(request);
        Sector updated = sectorUseCase.update(id, sector);
        return ResponseEntity.ok(sectorMapper.toResponse(updated));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar sector económico (solo ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectorUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private Sector mapRequestToDomain(SectorRequest request) {
        return Sector.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .build();
    }
}
