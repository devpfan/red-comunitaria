package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.UpdateRolRequest;
import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.application.mapper.UsuarioMapper;
import com.redcomunitaria.backend.domain.model.Usuario;
import com.redcomunitaria.backend.domain.port.in.UsuarioAdminUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST: Administración de usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Administración de Usuarios", description = "Endpoints para administración de usuarios (solo ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioAdminController {
    
    private final UsuarioAdminUseCase usuarioAdminUseCase;
    private final UsuarioMapper usuarioMapper;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios registrados")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioAdminUseCase.getAllUsuarios();
        List<UsuarioResponse> response = usuarios.stream()
                .map(usuarioMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario específico")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioAdminUseCase.getUsuarioById(id);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
    
    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activar usuario", description = "Activa un usuario desactivado")
    public ResponseEntity<UsuarioResponse> activarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioAdminUseCase.activarUsuario(id);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
    
    @PatchMapping("/{id}/desactivar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario activo")
    public ResponseEntity<UsuarioResponse> desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioAdminUseCase.desactivarUsuario(id);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
    
    @PatchMapping("/{id}/rol")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cambiar rol de usuario", description = "Cambia el rol de un usuario (USER o ADMIN)")
    public ResponseEntity<UsuarioResponse> cambiarRol(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRolRequest request) {
        Usuario usuario = usuarioAdminUseCase.cambiarRol(id, request.getRol());
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
}
