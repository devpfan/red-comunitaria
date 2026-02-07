package com.redcomunitaria.backend.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Archivo de emprendimiento
 */
@Entity
@Table(name = "archivo_emprendimiento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoEmprendimientoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "emprendimiento_id", nullable = false)
    private Long emprendimientoId;
    
    @Column(name = "nombre_original", nullable = false)
    private String nombreOriginal;
    
    @Column(name = "nombre_almacenado", nullable = false, unique = true)
    private String nombreAlmacenado;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_archivo", nullable = false, length = 50)
    private TipoArchivoEntity tipoArchivo;
    
    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;
    
    @Column(nullable = false)
    private Long tamanio;
    
    @Column(name = "ruta_archivo", nullable = false, columnDefinition = "TEXT")
    private String rutaArchivo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
