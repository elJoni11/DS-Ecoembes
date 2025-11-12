package Ecoembes.facade;

import Ecoembes.service.AsignacionService;
import Ecoembes.service.PlantaService;
import Ecoembes.service.LoginService;
import Ecoembes.dto.AsignacionDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller para gestión de asignaciones (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de asignación
 */
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
    public AsignacionDTO AsignarContenedor(String contenedorID, String plantaID, String token) {
        // Validaciones previas
        if (contenedorID == null || contenedorID.isEmpty()) {
            throw new IllegalArgumentException("El ID del contenedor es obligatorio");
        }
        
        if (plantaID == null || plantaID.isEmpty()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token es obligatorio");
        }
        
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Verificar capacidad de la planta
        int capacidadDisponible = plantaService.getCapacidad(plantaID);
        
        if (capacidadDisponible <= 0) {
            throw new IllegalStateException("La planta " + plantaID + " no tiene capacidad disponible");
        }
        
        // Realizar la asignación
        AsignacionDTO asignacion = asignacionService.asignarContenedor(contenedorID, plantaID);
        
        // Reducir la capacidad de la planta
        plantaService.reducirCapacidad(plantaID, 1);
        
        // Enviar notificación automáticamente
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
    public AsignacionDTO AsignarContenedores(List<String> contenedoresIDs, String plantaID, String token) {
        // Validaciones
        if (contenedoresIDs == null || contenedoresIDs.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un contenedor");
        }
        
        if (plantaID == null || plantaID.isEmpty()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Verificar capacidad
        int cantidadContenedores = contenedoresIDs.size();
        if (!plantaService.tieneCapacidadSuficiente(plantaID, cantidadContenedores)) {
            throw new IllegalStateException("La planta no tiene capacidad suficiente para " + 
                                          cantidadContenedores + " contenedores");
        }
        
        // Realizar asignación
        AsignacionDTO asignacion = asignacionService.asignarContenedores(contenedoresIDs, plantaID);
        
        // Reducir capacidad
        plantaService.reducirCapacidad(plantaID, cantidadContenedores);
        
        // Notificar
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
    public List<AsignacionDTO> getAsignacionesByPlanta(String plantaID, String token) {
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
    public List<AsignacionDTO> getAsignacionesByFecha(LocalDate fecha, String token) {
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return asignacionService.getAsignacionesByFecha(fecha);
    }
    
    /**
     * Obtiene el servicio de asignaciones
     * @return AsignacionService
     */
    public AsignacionService getAsignacionService() {
        return asignacionService;
    }
    
    /**
     * Establece el servicio de asignaciones
     * @param asignacionService servicio a establecer
     */
    public void setAsignacionService(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }
    
    /**
     * Obtiene el servicio de plantas
     * @return PlantaService
     */
    public PlantaService getPlantaService() {
        return plantaService;
    }
    
    /**
     * Establece el servicio de plantas
     * @param plantaService servicio a establecer
     */
    public void setPlantaService(PlantaService plantaService) {
        this.plantaService = plantaService;
    }
    
    /**
     * Obtiene el servicio de login
     * @return LoginService
     */
    public LoginService getLoginService() {
        return loginService;
    }
    
    /**
     * Establece el servicio de login
     * @param loginService servicio a establecer
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
