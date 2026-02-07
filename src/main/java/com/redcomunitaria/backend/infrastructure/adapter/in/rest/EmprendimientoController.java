package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoCreateRequest;
import com.redcomunitaria.backend.application.dto.request.EmprendimientoUpdateRequest;
import com.redcomunitaria.backend.application.dto.response.EmprendimientoResponse;
import com.redcomunitaria.backend.application.mapper.EmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.EmprendimientoUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST: Emprendimientos
 */
@RestController
@RequestMapping("/api/emprendimientos")
@RequiredArgsConstructor
@Tag(name = "Emprendimientos", description = "Endpoints para gestión de emprendimientos")
@SecurityRequirement(name = "bearerAuth")
public class EmprendimientoController {
    
    private final EmprendimientoUseCase emprendimientoUseCase;
    private final EmprendimientoMapper mapper;
    
    @GetMapping
    @Operation(summary = "Listar emprendimientos")
    public ResponseEntity<List<EmprendimientoResponse>> getAll() {
        var response = emprendimientoUseCase.getAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener emprendimiento por ID")
    public ResponseEntity<EmprendimientoResponse> getById(@PathVariable Long id) {
        var emprendimiento = emprendimientoUseCase.getById(id);
        return ResponseEntity.ok(mapper.toResponse(emprendimiento));
    }
    
    @PostMapping
    @Operation(summary = "Crear emprendimiento")
    public ResponseEntity<EmprendimientoResponse> create(@Valid @RequestBody EmprendimientoCreateRequest request) {
        Emprendimiento emprendimiento = toDomain(request);
        Emprendimiento saved = emprendimientoUseCase.create(emprendimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar emprendimiento")
    public ResponseEntity<EmprendimientoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmprendimientoUpdateRequest request) {
        Emprendimiento emprendimiento = toDomain(request);
        Emprendimiento updated = emprendimientoUseCase.update(id, emprendimiento);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar emprendimiento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emprendimientoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private Emprendimiento toDomain(EmprendimientoCreateRequest request) {
        return Emprendimiento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fechaCreacion(request.getFechaCreacion())
                .sector(request.getSector())
                .numeroEmpleados(request.getNumeroEmpleados())
                .inversionInicial(request.getInversionInicial())
                .estado(request.getEstado())
                .usuario(Usuario.builder().id(request.getUsuarioId()).build())
                .tipoEmprendimiento(TipoEmprendimiento.builder().id(request.getTipoEmprendimientoId()).build())
                .region(Region.builder().id(request.getRegionId()).build())
                .build();
    }
    
    private Emprendimiento toDomain(EmprendimientoUpdateRequest request) {
        return Emprendimiento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fechaCreacion(request.getFechaCreacion())
                .sector(request.getSector())
                .numeroEmpleados(request.getNumeroEmpleados())
                .inversionInicial(request.getInversionInicial())
                .estado(request.getEstado())
                .usuario(Usuario.builder().id(request.getUsuarioId()).build())
                .tipoEmprendimiento(TipoEmprendimiento.builder().id(request.getTipoEmprendimientoId()).build())
                .region(Region.builder().id(request.getRegionId()).build())
                .build();
    }
}
