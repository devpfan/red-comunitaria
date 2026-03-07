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

import com.redcomunitaria.backend.application.dto.request.RegionRequest;
import com.redcomunitaria.backend.application.dto.response.RegionResponse;
import com.redcomunitaria.backend.application.mapper.RegionMapper;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.port.in.RegionUseCase;

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
 * Controlador REST: Regiones
 */
@RestController
@RequestMapping("/api/regiones")
@RequiredArgsConstructor
@Tag(name = "Regiones", description = "Endpoints para consulta de regiones")
@SecurityRequirement(name = "bearerAuth")
public class RegionController {
    
    private final RegionUseCase regionUseCase;
    private final RegionMapper regionMapper;
    
    @GetMapping
    @Operation(summary = "Listar regiones")
    public ResponseEntity<List<RegionResponse>> getAll() {
        var response = regionUseCase.getAll().stream()
                .map(regionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener región por ID")
    public ResponseEntity<RegionResponse> getById(@PathVariable Long id) {
        var region = regionUseCase.getById(id);
        return ResponseEntity.ok(regionMapper.toResponse(region));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear región (solo ADMIN)",
               description = "Crea una nueva región con código DIVIPOLA. Requiere rol ADMIN.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Región creada exitosamente",
                     content = @Content(schema = @Schema(implementation = RegionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos de ADMIN", content = @Content)
    })
    public ResponseEntity<RegionResponse> create(@Valid @RequestBody RegionRequest request) {
        Region region = mapRequestToDomain(request);
        Region saved = regionUseCase.create(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionMapper.toResponse(saved));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar región (solo ADMIN)")
    public ResponseEntity<RegionResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RegionRequest request) {
        Region region = mapRequestToDomain(request);
        Region updated = regionUseCase.update(id, region);
        return ResponseEntity.ok(regionMapper.toResponse(updated));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar región (solo ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private Region mapRequestToDomain(RegionRequest request) {
        return Region.builder()
                .codigoDivipola(request.getCodigoDivipola())
                .departamento(request.getDepartamento())
                .municipio(request.getMunicipio())
                .corregimiento(request.getCorregimiento())
                .poblacion(request.getPoblacion())
                .build();
    }
}
