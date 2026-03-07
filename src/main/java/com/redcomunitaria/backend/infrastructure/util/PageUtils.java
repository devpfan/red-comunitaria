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
        if (sort.length == 0 || (sort.length == 1 && sort[0].isEmpty())) {
            return PageRequest.of(page, size);
        }
        
        Sort.Order[] orders = new Sort.Order[sort.length];
        
        for (int i = 0; i < sort.length; i++) {
            String[] sortParams = sort[i].split(",");
            String property = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
            
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
