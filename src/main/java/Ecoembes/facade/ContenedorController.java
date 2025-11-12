package Ecoembes.facade;

import Ecoembes.service.ContenedorService;
import Ecoembes.dto.ContenedorDTO;
import java.time.LocalDate;
import java.util.List;

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
        
        if (contenedor.getUbicacion() == null || contenedor.getUbicacion().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del contenedor es obligatoria");
        }
        
        if (contenedor.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que 0");
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
     * Obtiene contenedores por código postal (zona)
     * @param codPostal código postal de la zona
     * @return lista de contenedores en esa zona
     */
    public List<ContenedorDTO> getContenedoresByZona(int codPostal) {
        return contenedorService.getContenedoresByZona(codPostal);
    }
    
    /**
     * Obtiene contenedores actualizados en una fecha específica
     * @param fecha fecha de actualización
     * @return lista de contenedores actualizados en esa fecha
     */
    public List<ContenedorDTO> getContenedorByFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        return contenedorService.getContenedorByFecha(fecha);
    }
    
    /**
     * Obtiene un contenedor por su ID
     * @param contenedorID ID del contenedor
     * @return ContenedorDTO o null si no existe
     */
    public ContenedorDTO getContenedorById(String contenedorID) {
        if (contenedorID == null || contenedorID.isEmpty()) {
            throw new IllegalArgumentException("El ID del contenedor es obligatorio");
        }
        return contenedorService.getContenedorById(contenedorID);
    }
    
    /**
     * Obtiene todos los contenedores del sistema
     * @return lista de todos los contenedores
     */
    public List<ContenedorDTO> getAllContenedores() {
        return contenedorService.getAllContenedores();
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
