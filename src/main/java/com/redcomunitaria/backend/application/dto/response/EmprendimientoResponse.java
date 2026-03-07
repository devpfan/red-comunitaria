package com.redcomunitaria.backend.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.redcomunitaria.backend.domain.model.EstadoEmprendimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: Respuesta de Emprendimiento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoResponse {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
    
    private Long sectorId;
    private String sectorCodigo;
    private String sectorNombre;
    
    private Integer numeroEmpleados;
    private BigDecimal inversionInicial;
    private EstadoEmprendimiento estado;
    
    private Long usuarioId;
    private String usuarioNombre;
    
    private Long tipoEmprendimientoId;
    private String tipoEmprendimientoNombre;
    
    private Long regionId;
    private String regionDepartamento;
    private String regionMunicipio;
}
