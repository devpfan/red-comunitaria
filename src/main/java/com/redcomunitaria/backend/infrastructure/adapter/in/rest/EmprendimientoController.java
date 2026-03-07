package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.EmprendimientoCreateRequest;
import com.redcomunitaria.backend.application.dto.request.EmprendimientoFiltros;
import com.redcomunitaria.backend.application.dto.request.EmprendimientoUpdateRequest;
import com.redcomunitaria.backend.application.dto.response.EmprendimientoResponse;
import com.redcomunitaria.backend.application.mapper.EmprendimientoMapper;
import com.redcomunitaria.backend.domain.model.Emprendimiento;
import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Region;
import com.redcomunitaria.backend.domain.model.Sector;
import com.redcomunitaria.backend.domain.model.TipoEmprendimiento;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.EmprendimientoUseCase;
import com.redcomunitaria.backend.infrastructure.util.PageUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar emprendimientos con paginación", 
               description = "Retorna emprendimientos paginados. Parámetros: page, size, sort")
    public ResponseEntity<Page<EmprendimientoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        
        Pageable pageable = PageUtils.createPageable(page, size, sort);
        Page<EmprendimientoResponse> response = emprendimientoUseCase.getAll(pageable)
                .map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar emprendimientos con filtros", 
               description = "Búsqueda avanzada con múltiples filtros y paginación")
    public ResponseEntity<Page<EmprendimientoResponse>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long sectorId,
            @RequestParam(required = false) Long tipoEmprendimientoId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) EstadoEmprendimiento estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacionDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacionHasta,
            @RequestParam(required = false) BigDecimal inversionMinima,
            @RequestParam(required = false) BigDecimal inversionMaxima,
            @RequestParam(required = false) Integer empleadosMinimo,
            @RequestParam(required = false) Integer empleadosMaximo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        
        EmprendimientoFiltros filtros = EmprendimientoFiltros.builder()
                .nombre(nombre)
                .sectorId(sectorId)
                .tipoEmprendimientoId(tipoEmprendimientoId)
                .regionId(regionId)
                .departamento(departamento)
                .municipio(municipio)
                .estado(estado)
                .fechaCreacionDesde(fechaCreacionDesde)
                .fechaCreacionHasta(fechaCreacionHasta)
                .inversionMinima(inversionMinima)
                .inversionMaxima(inversionMaxima)
                .empleadosMinimo(empleadosMinimo)
                .empleadosMaximo(empleadosMaximo)
                .build();
        
        Pageable pageable = PageUtils.createPageable(page, size, sort);
        Page<EmprendimientoResponse> response = emprendimientoUseCase.buscar(filtros, pageable)
                .map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener emprendimiento por ID",
               description = "Retorna los detalles completos de un emprendimiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Emprendimiento encontrado"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado")
    })
    public ResponseEntity<EmprendimientoResponse> getById(@PathVariable Long id) {
        var emprendimiento = emprendimientoUseCase.getById(id);
        return ResponseEntity.ok(mapper.toResponse(emprendimiento));
    }
    
    @PostMapping
    @Operation(summary = "Crear emprendimiento",
               description = "Crea un nuevo emprendimiento con todos sus datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Emprendimiento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<EmprendimientoResponse> create(@Valid @RequestBody EmprendimientoCreateRequest request) {
        Emprendimiento emprendimiento = toDomain(request);
        Emprendimiento saved = emprendimientoUseCase.create(emprendimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(saved));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar emprendimiento",
               description = "Actualiza un emprendimiento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Emprendimiento actualizado"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para actualizar")
    })
    public ResponseEntity<EmprendimientoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EmprendimientoUpdateRequest request) {
        Emprendimiento emprendimiento = toDomain(request);
        Emprendimiento updated = emprendimientoUseCase.update(id, emprendimiento);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar emprendimiento",
               description = "Elimina permanentemente un emprendimiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Emprendimiento eliminado"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado"),
        @ApiResponse(responseCode = "403", description = "Sin permisos para eliminar")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emprendimientoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    private Emprendimiento toDomain(EmprendimientoCreateRequest request) {
        return Emprendimiento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .fechaCreacion(request.getFechaCreacion())
                .sector(Sector.builder().id(request.getSectorId()).build())
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
                .sector(Sector.builder().id(request.getSectorId()).build())
                .numeroEmpleados(request.getNumeroEmpleados())
                .inversionInicial(request.getInversionInicial())
                .estado(request.getEstado())
                .usuario(Usuario.builder().id(request.getUsuarioId()).build())
                .tipoEmprendimiento(TipoEmprendimiento.builder().id(request.getTipoEmprendimientoId()).build())
                .region(Region.builder().id(request.getRegionId()).build())
                .build();
    }
}
