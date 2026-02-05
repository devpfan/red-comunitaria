package com.redcomunitaria.backend.application.dto.request;

import com.redcomunitaria.backend.domain.model.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Request para actualizar el rol de un usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para actualizar el rol de un usuario")
public class UpdateRolRequest {
    
    @NotNull(message = "El rol es obligatorio")
    @Schema(description = "Nuevo rol del usuario", example = "ADMIN")
    private Rol rol;
}
