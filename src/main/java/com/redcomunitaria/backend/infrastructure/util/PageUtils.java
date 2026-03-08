package com.redcomunitaria.backend.infrastructure.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utilidades para paginación
 */
public class PageUtils {
    
    private PageUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Crea un Pageable a partir de parámetros de paginación
     * 
     * @param page número de página (0-based)
     * @param size tamaño de página
     * @param sort array con formato ["campo,dirección"] ej: ["id,desc", "nombre,asc"]
     * @return Pageable configurado
     */
    public static Pageable createPageable(int page, int size, String[] sort) {
        if (sort == null || sort.length == 0 || (sort.length == 1 && sort[0].isEmpty())) {
            return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        }
        
        Sort.Order[] orders = new Sort.Order[sort.length];
        
        for (int i = 0; i < sort.length; i++) {
            String sortParam = sort[i].trim();
            if (sortParam.isEmpty()) {
                continue;
            }
            
            String[] sortParams = sortParam.split(",");
            String property = sortParams[0].trim();
            
            // Validar que la propiedad no esté vacía
            if (property.isEmpty()) {
                continue;
            }
            
            Sort.Direction direction = Sort.Direction.DESC;
            if (sortParams.length > 1) {
                String directionStr = sortParams[1].trim();
                if (directionStr.equalsIgnoreCase("asc")) {
                    direction = Sort.Direction.ASC;
                }
            }
            
            orders[i] = new Sort.Order(direction, property);
        }
        
        return PageRequest.of(page, size, Sort.by(orders));
    }
    
    /**
     * Crea un Pageable simple sin ordenamiento
     * 
     * @param page número de página (0-based)
     * @param size tamaño de página
     * @return Pageable sin ordenamiento
     */
    public static Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size);
    }
    
    /**
     * Crea un Pageable con ordenamiento simple
     * 
     * @param page número de página (0-based)
     * @param size tamaño de página
     * @param property campo para ordenar
     * @param direction dirección de ordenamiento
     * @return Pageable con ordenamiento simple
     */
    public static Pageable createPageable(int page, int size, String property, Sort.Direction direction) {
        return PageRequest.of(page, size, Sort.by(direction, property));
    }
}
