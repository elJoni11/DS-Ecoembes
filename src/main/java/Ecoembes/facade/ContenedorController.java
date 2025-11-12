package Ecoembes.facade;

import Ecoembes.dto.ContenedorDTO;
import Ecoembes.service.ContenedorService

/**
 * Controller para gestión de contenedores (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de contenedores
 */
public class ContenedorController {
    
    private ContenedorService contenedorService;
    
    /**
     * Constructor con inyección de dependencias
     */
    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }
    
    /**
     * Constructor por defecto
     */
    public ContenedorController() {
        this.contenedorService = new ContenedorService();
    }
    
    /**
     * Crea un nuevo contenedor en el sistema
     * @param contenedor DTO con los datos del contenedor a crear
     * @return ContenedorDTO con el contenedor creado
     */
    public ContenedorDTO CrearContenedor(ContenedorDTO contenedor) {
        // Validaciones previas
        if (contenedor == null) {
            throw new IllegalArgumentException("El contenedor no puede ser nulo");
        }
        
        if (contenedor.getTipo() == null || contenedor.getTipo().isEmpty()) {
            throw new IllegalArgumentException("El tipo de contenedor es obligatorio");
        }
        
        if (contenedor.getZona() == null || contenedor.getZona().isEmpty()) {
            throw new IllegalArgumentException("La zona es obligatoria");
        }
        
        // Delegar al servicio
        return contenedorService.crearContenedor(contenedor);
    }
    
    /**
     * Actualiza la información de un contenedor existente
     * @param contenedor DTO con los datos actualizados del contenedor
     * @return ContenedorDTO con el contenedor actualizado
     */
    public ContenedorDTO ActualizarContenedor(ContenedorDTO contenedor) {
        // Validaciones previas
        if (contenedor == null) {
            throw new IllegalArgumentException("El contenedor no puede ser nulo");
        }
        
        if (contenedor.getContenedorID() == null || contenedor.getContenedorID().isEmpty()) {
            throw new IllegalArgumentException("El ID del contenedor es obligatorio para actualizar");
        }
        
        // Delegar al servicio
        return contenedorService.actualizarContenedor(contenedor);
    }
    
    /**
     * Obtiene el servicio de contenedores
     * @return ContenedorService
     */
    public ContenedorService getContenedorService() {
        return contenedorService;
    }
    
    /**
     * Establece el servicio de contenedores
     * @param contenedorService servicio a establecer
     */
    public void setContenedorService(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }
}