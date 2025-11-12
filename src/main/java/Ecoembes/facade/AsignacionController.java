package Ecoembes.facade;

import Ecoembes.dto.AsignacionDTO;
import Ecoembes.service.AsignacionService;
import Ecoembes.service.PlantaService;
import Ecoembes.service.LoginService;

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
        this.asignacionService = new AsignacionService(null, plantaService);
        this.plantaService = new PlantaService();
        this.loginService = new LoginService();
    }
    
    /**
     * Asigna un contenedor a una planta
     * Coordina la verificación de capacidad y la asignación
     * @param contenedorId ID del contenedor a asignar
     * @param plantaId ID de la planta destino
     * @param token token de sesión para validación
     * @return AsignacionDTO con los datos de la asignación creada
     */
    public AsignacionDTO AsignarContenedor(String contenedorId, String plantaId, String token) {
        // Validaciones previas
        if (contenedorId == null || contenedorId.isEmpty()) {
            throw new IllegalArgumentException("El ID del contenedor es obligatorio");
        }
        
        if (plantaId == null || plantaId.isEmpty()) {
            throw new IllegalArgumentException("El ID de la planta es obligatorio");
        }
        
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token es obligatorio");
        }
        
        // Validar sesión
        if (loginService.validarToken(token) == null) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Verificar capacidad de la planta
        int capacidadDisponible = plantaService.getCapacidad(plantaId);
        
        if (capacidadDisponible <= 0) {
            throw new IllegalStateException("La planta " + plantaId + " no tiene capacidad disponible");
        }
        
        // Realizar la asignación
        AsignacionDTO asignacion = asignacionService.asignarContenedor(contenedorId, plantaId);
        
        // Enviar notificación automáticamente
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
        if (loginService.validarToken(token) == null) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Enviar notificación
        asignacionService.enviarNotificacion(asignacion);
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