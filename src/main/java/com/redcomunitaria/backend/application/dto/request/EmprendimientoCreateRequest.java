package com.redcomunitaria.backend.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Crear emprendimiento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoCreateRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDate fechaCreacion;
    
    private String sector;
    
    @PositiveOrZero(message = "El número de empleados debe ser positivo")
    private Integer numeroEmpleados;
    
    @PositiveOrZero(message = "La inversión inicial debe ser positiva")
    private BigDecimal inversionInicial;
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoEmprendimiento estado;
    
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
    
    @NotNull(message = "El tipo de emprendimiento es obligatorio")
    private Long tipoEmprendimientoId;
    
    @NotNull(message = "La región es obligatoria")
    private Long regionId;
}
