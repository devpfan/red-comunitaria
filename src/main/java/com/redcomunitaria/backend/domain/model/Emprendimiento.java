package com.redcomunitaria.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio: Emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emprendimiento {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String sector;
    private Integer numeroEmpleados;
    private BigDecimal inversionInicial;
    private EstadoEmprendimiento estado;
    private Usuario usuario;
    private TipoEmprendimiento tipoEmprendimiento;
    private Region region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
