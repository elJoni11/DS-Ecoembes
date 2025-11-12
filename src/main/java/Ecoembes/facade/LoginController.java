package Ecoembes.facade;

import Ecoembes.dto.EmpleadoDTO;
import Ecoembes.service.LoginService;

/**
 * Controller para gestión de login y tokens (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de autenticación
 */
public class LoginController {
    
    private LoginService loginService;
    
    /**
     * Constructor con inyección de dependencias
     */
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    
    /**
     * Constructor por defecto
     */
    public LoginController() {
        this.loginService = new LoginService();
    }
    
    /**
     * Genera un token de sesión para un empleado
     * @param empleado datos del empleado para el cual generar el token
     * @return token de sesión generado
     */
    public String generarToken(EmpleadoDTO empleado) {
        // Validaciones previas
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }
        
        if (empleado.getId() == null || empleado.getId().isEmpty()) {
            throw new IllegalArgumentException("El ID del empleado es obligatorio");
        }
        
        // Generar token
        return loginService.generarToken(empleado);
    }
    
    /**
     * Valida si un token de sesión es válido
     * @param token token a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validarToken(String token) {
        // Validaciones previas
        if (token == null || token.isEmpty()) {
            return false;
        }
        
        // Validar token
        return loginService.validarToken(token);
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