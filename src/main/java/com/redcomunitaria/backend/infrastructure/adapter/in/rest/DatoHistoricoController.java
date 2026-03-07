package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.DatoHistoricoRequest;
import com.redcomunitaria.backend.application.dto.response.DatoHistoricoResponse;
import com.redcomunitaria.backend.application.mapper.DatoHistoricoMapper;
import com.redcomunitaria.backend.domain.model.DatoHistorico;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.DatoHistoricoUseCase;
import com.redcomunitaria.backend.domain.port.out.UsuarioRepositoryPort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller: Datos Históricos de emprendimientos
 */
@RestController
@RequestMapping("/api/emprendimientos/{emprendimientoId}/datos-historicos")
@RequiredArgsConstructor
@Tag(name = "Datos Históricos", description = "Gestión de datos históricos anuales de emprendimientos")
@SecurityRequirement(name = "bearerAuth")
public class DatoHistoricoController {
    
    private final DatoHistoricoUseCase datoHistoricoUseCase;
    private final UsuarioRepositoryPort usuarioRepository;
    
    @PostMapping
    @Operation(summary = "Crear dato histórico", 
               description = "Agrega datos de un año específico (ingresos, empleos, innovaciones)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dato histórico creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe dato para este año")
    })
    public ResponseEntity<DatoHistoricoResponse> create(
            @PathVariable Long emprendimientoId,
            @Valid @RequestBody DatoHistoricoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        DatoHistorico datoHistorico = datoHistoricoUseCase.create(emprendimientoId, request, usuario.getId());
        DatoHistoricoResponse response = DatoHistoricoMapper.toResponse(datoHistorico);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    @Operation(summary = "Listar datos históricos", 
               description = "Obtiene todos los datos históricos de un emprendimiento ordenados por año")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "404", description = "Emprendimiento no encontrado")
    })
    public ResponseEntity<List<DatoHistoricoResponse>> getAll(@PathVariable Long emprendimientoId) {
        
        List<DatoHistorico> datosHistoricos = datoHistoricoUseCase.getByEmprendimiento(emprendimientoId);
        List<DatoHistoricoResponse> response = datosHistoricos.stream()
                .map(DatoHistoricoMapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{anio}")
    @Operation(summary = "Obtener dato histórico por año", 
               description = "Obtiene el dato histórico de un año específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dato encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe dato para este año")
    })
    public ResponseEntity<DatoHistoricoResponse> getByAnio(
            @PathVariable Long emprendimientoId,
            @PathVariable Integer anio) {
        
        DatoHistorico datoHistorico = datoHistoricoUseCase.getByEmprendimientoAndAnio(emprendimientoId, anio);
        DatoHistoricoResponse response = DatoHistoricoMapper.toResponse(datoHistorico);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{anio}")
    @Operation(summary = "Actualizar dato histórico", description = "Actualiza los datos de un año específico")
    public ResponseEntity<DatoHistoricoResponse> update(
            @PathVariable Long emprendimientoId,
            @PathVariable Integer anio,
            @Valid @RequestBody DatoHistoricoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        DatoHistorico datoHistorico = datoHistoricoUseCase.update(emprendimientoId, anio, request, usuario.getId());
        DatoHistoricoResponse response = DatoHistoricoMapper.toResponse(datoHistorico);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{anio}")
    @Operation(summary = "Eliminar dato histórico", description = "Elimina el dato histórico de un año específico")
    public ResponseEntity<Void> delete(
            @PathVariable Long emprendimientoId,
            @PathVariable Integer anio,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        datoHistoricoUseCase.delete(emprendimientoId, anio, usuario.getId());
        
        return ResponseEntity.noContent().build();
    }
}
