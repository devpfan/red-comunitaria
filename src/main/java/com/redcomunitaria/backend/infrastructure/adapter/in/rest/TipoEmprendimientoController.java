package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.response.TipoEmprendimientoResponse;
import com.redcomunitaria.backend.application.mapper.TipoEmprendimientoMapper;
import com.redcomunitaria.backend.domain.port.in.TipoEmprendimientoUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
