package Ecoembes.facade;

import Ecoembes.service.AsignacionService;
import Ecoembes.service.PlantaService;
import Ecoembes.service.LoginService;
import Ecoembes.dto.AsignacionDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Controller para gestión de asignaciones (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de asignación
 */
@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {
    
    private AsignacionService asignacionService;
    private PlantaService plantaService;
    private LoginService loginService;
    
    /**
     * Constructor con inyección de dependencias
     */
    public AsignacionController(AsignacionService asignacionService, 
                                PlantaService plantaService,
                                LoginService loginService) {
        this.asignacionService = asignacionService;
        this.plantaService = plantaService;
        this.loginService = loginService;
    }
    
    /**
     * Constructor por defecto
     */
    public AsignacionController() {
        this.asignacionService = new AsignacionService();
        this.plantaService = new PlantaService();
        this.loginService = new LoginService();
    }
    
    /**
     * Asigna un contenedor a una planta
     * Coordina la verificación de capacidad y la asignación
     * @param contenedorID ID del contenedor a asignar
     * @param plantaID ID de la planta destino
     * @param token token de sesión para validación
     * @return AsignacionDTO con los datos de la asignación creada
     */
    @PostMapping("/asignarSimple")
    public AsignacionDTO AsignarContenedor(@RequestBody AsignacionSimpleRequest request, 
    		@RequestHeader("X-Auth-Token") String token) {
        
    	String contenedorID = request.getContenedorID();
        String plantaID = request.getPlantaID();
    	
        // 1. Validar sesión
        if (token == null || !loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // 2. Verificar capacidad (Lógica Facade)
        int capacidadDisponible = plantaService.getCapacidad(plantaID); // Asumiendo este método existe
        if (capacidadDisponible <= 0) {
            throw new IllegalStateException("La planta " + plantaID + " no tiene capacidad disponible");
        }
        
        // 3. Realizar la asignación y reducir capacidad
        AsignacionDTO asignacion = asignacionService.asignarContenedor(contenedorID, plantaID);
        plantaService.reducirCapacidad(plantaID, 1); // Asumiendo este método existe
        
        // 4. Enviar notificación
        asignacionService.enviarNotificacion(asignacion);
        
        return asignacion;
    }
    
    /**
     * Asigna múltiples contenedores a una planta
     * @param contenedoresIDs lista de IDs de contenedores
     * @param plantaID ID de la planta destino
     * @param token token de sesión para validación
     * @return AsignacionDTO con los datos de la asignación creada
     */
    @PostMapping("/asignarMultiple")
    public AsignacionDTO AsignarContenedores(@RequestBody AsignacionMultipleRequest request, 
    		@RequestHeader("X-Auth-Token") String token) {
        
    	List<String> contenedoresIDs = request.getContenedoresIDs();
        String plantaID = request.getPlantaID();
        int cantidadContenedores = contenedoresIDs != null ? contenedoresIDs.size() : 0;
    	
        // 1. Validar sesión y datos
        if (token == null || !loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        if (cantidadContenedores == 0) {
            throw new IllegalArgumentException("Debe proporcionar al menos un contenedor");
        }

        // 2. Verificar capacidad
        if (!plantaService.tieneCapacidadSuficiente(plantaID, cantidadContenedores)) { // Asumiendo este método existe
            throw new IllegalStateException("La planta no tiene capacidad suficiente");
        }
        
        // 3. Realizar asignación y reducir capacidad
        AsignacionDTO asignacion = asignacionService.asignarContenedores(contenedoresIDs, plantaID);
        plantaService.reducirCapacidad(plantaID, cantidadContenedores);
        
        // 4. Notificar
        asignacionService.enviarNotificacion(asignacion);
        
        return asignacion;
    }
    
    /**
     * Envía una notificación sobre una asignación
     * @param asignacion datos de la asignación
     * @param token token de sesión para validación
     */
    public void EnviarNotificacion(AsignacionDTO asignacion, String token) {
        // Validaciones previas
        if (asignacion == null) {
            throw new IllegalArgumentException("La asignación no puede ser nula");
        }
        
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token es obligatorio");
        }
        
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Enviar notificación
        asignacionService.enviarNotificacion(asignacion);
    }
    
    /**
     * Obtiene asignaciones por planta
     * @param plantaID ID de la planta
     * @param token token de sesión para validación
     * @return lista de asignaciones de la planta
     */
    @GetMapping("/porPlanta")
    public List<AsignacionDTO> getAsignacionesByPlanta(@PathVariable String plantaID, 
    		@RequestHeader("X-Auth-Token") String token) {
    	// Validar sesión
    	if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return asignacionService.getAsignacionesByPlanta(plantaID);
    }
    
    /**
     * Obtiene asignaciones por fecha
     * @param fecha fecha de las asignaciones
     * @param token token de sesión para validación
     * @return lista de asignaciones de la fecha
     */
    @GetMapping("/porFecha")
    public List<AsignacionDTO> getAsignacionesByFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, 
    		@RequestHeader("X-Auth-Token") String token) {
        // Validar sesión
    	if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return asignacionService.getAsignacionesByFecha(fecha);
    }
    
// --- Clases Auxiliares (Para recibir el JSON del Body) ---
    
    public static class AsignacionSimpleRequest {
        private String contenedorID;
        private String plantaID;
        
        // Getters y Setters...
        public String getContenedorID() { return contenedorID; }
        public void setContenedorID(String contenedorID) { this.contenedorID = contenedorID; }
        public String getPlantaID() { return plantaID; }
        public void setPlantaID(String plantaID) { this.plantaID = plantaID; }
    }
    
    public static class AsignacionMultipleRequest {
        private List<String> contenedoresIDs;
        private String plantaID;
        
        // Getters y Setters...
        public List<String> getContenedoresIDs() { return contenedoresIDs; }
        public void setContenedoresIDs(List<String> contenedoresIDs) { this.contenedoresIDs = contenedoresIDs; }
        public String getPlantaID() { return plantaID; }
        public void setPlantaID(String plantaID) { this.plantaID = plantaID; }
    }
}
