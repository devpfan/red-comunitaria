package com.redcomunitaria.backend.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redcomunitaria.backend.application.dto.request.ChangePasswordRequest;
import com.redcomunitaria.backend.application.dto.request.ForgotPasswordRequest;
import com.redcomunitaria.backend.application.dto.request.LoginRequest;
import com.redcomunitaria.backend.application.dto.request.RegisterRequest;
import com.redcomunitaria.backend.application.dto.request.ResetPasswordRequest;
import com.redcomunitaria.backend.application.dto.request.UpdateProfileRequest;
import com.redcomunitaria.backend.application.dto.response.AuthResponse;
import com.redcomunitaria.backend.application.dto.response.MessageResponse;
import com.redcomunitaria.backend.application.dto.response.UsuarioResponse;
import com.redcomunitaria.backend.domain.port.in.AuthUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST: Autenticación
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints de autenticación y gestión de contraseñas")
public class AuthController {
    
    private final AuthUseCase authUseCase;
    
    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", 
               description = "Crea una cuenta de usuario y retorna token JWT para acceso inmediato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Email ya registrado")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authUseCase.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión",
               description = "Autentica un usuario con email y contraseña, retorna token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso",
                content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "401", description = "Email o contraseña incorrectos")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authUseCase.login(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    @Operation(summary = "Obtener usuario actual",
               description = "Retorna la información del usuario autenticado mediante el token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente",
                content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "401", description = "Token inválido o expirado")
    })
    public ResponseEntity<UsuarioResponse> getCurrentUser() {
        UsuarioResponse response = authUseCase.getCurrentUser();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar reseteo de contraseña", 
               description = "Genera un código de 6 dígitos y lo envía por consola (simulación de email)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Código generado exitosamente",
                content = @Content(schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(responseCode = "404", description = "Email no encontrado")
    })
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        MessageResponse response = authUseCase.forgotPassword(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reset-password")
    @Operation(summary = "Resetear contraseña con código", 
               description = "Valida el código de 6 dígitos y establece una nueva contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada exitosamente",
                content = @Content(schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(responseCode = "400", description = "Código inválido o expirado"),
        @ApiResponse(responseCode = "404", description = "Código no encontrado")
    })
    public ResponseEntity<MessageResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        MessageResponse response = authUseCase.resetPassword(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/change-password")
    @Operation(summary = "Cambiar contraseña", 
               description = "Permite al usuario autenticado cambiar su contraseña validando la actual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña cambiada exitosamente",
                content = @Content(schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(responseCode = "400", description = "Contraseña actual incorrecta"),
        @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        MessageResponse response = authUseCase.changePassword(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/profile")
    @Operation(summary = "Actualizar perfil", 
               description = "Permite al usuario autenticado actualizar su información personal (nombre, apellido, teléfono)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "Usuario no autenticado")
    })
    public ResponseEntity<UsuarioResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UsuarioResponse response = authUseCase.updateProfile(request);
        return ResponseEntity.ok(response);
    }
}
