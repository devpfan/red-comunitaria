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

import com.redcomunitaria.backend.application.dto.request.TipoEmprendimientoRequest;
import com.redcomunitaria.backend.application.dto.response.TipoEmprendimientoResponse;
import com.redcomunitaria.backend.application.mapper.TipoEmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.port.in.TipoEmprendimientoUseCase;

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
 * Controlador REST: Tipos de Emprendimiento
 */
@RestController
@RequestMapping("/api/tipos-emprendimiento")
@RequiredArgsConstructor
@Tag(name = "Tipos de Emprendimiento", description = "Endpoints para consulta de tipos")
@SecurityRequirement(name = "bearerAuth")
public class TipoEmprendimientoController {
    
    private final TipoEmprendimientoUseCase tipoUseCase;
    private final TipoEmprendimientoMapper tipoMapper;
  
    
    @GetMapping
    @Operation(summary = "Listar tipos de emprendimiento")
    public ResponseEntity<List<TipoEmprendimientoResponse>> getAll() {
        var response = tipoUseCase.getAll().stream()
                .map(tipoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo por ID")
    public ResponseEntity<TipoEmprendimientoResponse> getById(@PathVariable Long id) {
        var tipo = tipoUseCase.getById(id);
        return ResponseEntity.ok(tipoMapper.toResponse(tipo));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear tipo de emprendimiento (solo ADMIN)",
               description = "Crea un nuevo tipo de emprendimiento. Requiere rol ADMIN.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tipo creado exitosamente",
                     content = @Content(schema = @Schema(implementation = TipoEmprendimientoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Sin permisos de ADMIN", content = @Content)
    })
    public ResponseEntity<TipoEmprendimientoResponse> create(@Valid @RequestBody TipoEmprendimientoRequest request) {
        TipoEmprendimiento tipo = mapRequestToDomain(request);
        TipoEmprendimiento saved = tipoUseCase.create(tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMapper.toResponse(saved));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar tipo de emprendimiento (solo ADMIN)")
    public ResponseEntity<TipoEmprendimientoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TipoEmprendimientoRequest request) {
        TipoEmprendimiento tipo = mapRequestToDomain(request);
        TipoEmprendimiento updated = tipoUseCase.update(id, tipo);
        return ResponseEntity.ok(tipoMapper.toResponse(updated));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar tipo de emprendimiento (solo ADMIN)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private TipoEmprendimiento mapRequestToDomain(TipoEmprendimientoRequest request) {
        return TipoEmprendimiento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
    }
}
