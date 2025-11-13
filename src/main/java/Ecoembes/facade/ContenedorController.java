package Ecoembes.facade;

import Ecoembes.service.ContenedorService;
import Ecoembes.dto.ContenedorDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Controller para gestión de contenedores (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de contenedores
 */
@RestController
@RequestMapping("/api/contenedores")
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
    @PostMapping("/crear")
    public ContenedorDTO CrearContenedor(@RequestBody ContenedorDTO contenedor) {
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
    @PutMapping("/actualizar")
    public ContenedorDTO ActualizarContenedor(@RequestBody ContenedorDTO contenedor) {
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
     * Obtiene todos los contenedores del sistema
     * @return lista de todos los contenedores
     */
    @GetMapping("/todos")
    public List<ContenedorDTO> getAllContenedores() {
        return contenedorService.getAllContenedores();
    }
    
    /**
     * Obtiene un contenedor por su ID
     * @param contenedorID ID del contenedor
     * @return ContenedorDTO o null si no existe
     */
    @GetMapping("/{id}")
    public ContenedorDTO getContenedorById(@PathVariable("id") String contenedorID) {
        if (contenedorID == null || contenedorID.isEmpty()) {
            throw new IllegalArgumentException("El ID del contenedor es obligatorio");
        }
        return contenedorService.getContenedorById(contenedorID);
    }
    
    /**
     * Obtiene contenedores por código postal (zona)
     * @param codPostal código postal de la zona
     * @return lista de contenedores en esa zona
     */
    @GetMapping("/zona")
    public List<ContenedorDTO> getContenedoresByZona(@RequestParam int codPostal) {
        return contenedorService.getContenedoresByZona(codPostal);
    }
    
    /**
     * Obtiene contenedores actualizados en una fecha específica
     * @param fecha fecha de actualización
     * @return lista de contenedores actualizados en esa fecha
     */
    @GetMapping("/fecha")
    public List<ContenedorDTO> getContenedorByFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        return contenedorService.getContenedorByFecha(fecha);
    }
}
