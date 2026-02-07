package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.response.RegionResponse;
import com.redcomunitaria.backend.application.mapper.RegionMapper;
import com.redcomunitaria.backend.domain.port.in.RegionUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
