package Ecoembes.facade;

import Ecoembes.service.LoginService;
import Ecoembes.dto.EmpleadoDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gestión de login y tokens (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de autenticación
 */
@RestController
@RequestMapping("/api/login")
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
     * Valida si un token de sesión es válido
     * @param token token a validar
     * @return true si el token es válido, false en caso contrario
     */
    @GetMapping("/validarToken")
    public boolean validarToken(@RequestHeader("X-Auth-Token") String token) {
    	
    	// La lógica de la Facade aquí es delegar directamente al servicio
        boolean esValido = loginService.validarToken(token);
        
        if (!esValido) {
            // Si no es válido, lanzamos una excepción de seguridad. 
            // Spring la mapeará a un error HTTP 401 Unauthorized o 403 Forbidden.
            throw new SecurityException("Token inválido o expirado");
        }
        
        return true; // Devuelve 'true' y HTTP 200 OK si es válido
    }

    /**
     * Obtiene el ID del empleado asociado a un token
     * @param token token de sesión
     * @return ID del empleado o null si el token no es válido
     */
    @GetMapping("/empleadoId")
    public Long getEmpleadoIdFromToken(@RequestHeader("X-Auth-Token") String token) {
    	if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        return loginService.getEmpleadoIdFromToken(token);
    }
}
