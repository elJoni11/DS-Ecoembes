package Ecoembes.facade;

import Ecoembes.service.EmpleadoService;
import Ecoembes.service.LoginService;
import Ecoembes.dto.EmpleadoDTO;

/**
 * Controller para gestión de empleados (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de empleados
 */
public class EmpleadoController {
    
    private EmpleadoService empleadoService;
    private LoginService loginService;
    
    /**
     * Constructor con inyección de dependencias
     */
    public EmpleadoController(EmpleadoService empleadoService, LoginService loginService) {
        this.empleadoService = empleadoService;
        this.loginService = loginService;
    }
    
    /**
     * Constructor por defecto
     */
    public EmpleadoController() {
        this.empleadoService = new EmpleadoService();
        this.loginService = new LoginService();
    }
    
    /**
     * Inicia sesión de un empleado en el sistema
     * Coordina la autenticación y generación de token
     * @param email email del empleado
     * @param password contraseña del empleado
     * @return token de sesión generado
     */
    public String IniciarSesion(String email, String password) {
        // Validaciones previas
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        
        // 1. Autenticar al empleado
        EmpleadoDTO empleado = empleadoService.iniciarSesion(email, password);
        
        if (empleado == null) {
            throw new SecurityException("Credenciales inválidas");
        }
        
        // 2. Generar token de sesión
        String token = loginService.generarToken(empleado);
        
        // 3. Registrar la sesión en el servicio de empleados
        empleadoService.registrarSesion(email, token);
        
        return token;
    }
    
    /**
     * Cierra la sesión de un empleado
     * @param token token de sesión a cerrar
     */
    public void CerrarSesion(String token) {
        // Validaciones previas
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token no puede estar vacío");
        }
        
        // Validar que el token sea válido antes de cerrar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        // Cerrar sesión en ambos servicios
        empleadoService.cerrarSesion(token);
        loginService.invalidarToken(token);
    }
    
    /**
     * Obtiene información de un empleado por email
     * @param email email del empleado
     * @param token token de sesión para validación
     * @return EmpleadoDTO con la información del empleado
     */
    public EmpleadoDTO getEmpleadoByEmail(String email, String token) {
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return empleadoService.getEmpleadoByEmail(email);
    }
    
    /**
     * Obtiene información de un empleado por ID
     * @param id ID del empleado
     * @param token token de sesión para validación
     * @return EmpleadoDTO con la información del empleado
     */
    public EmpleadoDTO getEmpleadoById(Long id, String token) {
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return empleadoService.getEmpleadoById(id);
    }
    
    /**
     * Obtiene el servicio de empleados
     * @return EmpleadoService
     */
    public EmpleadoService getEmpleadoService() {
        return empleadoService;
    }
    
    /**
     * Establece el servicio de empleados
     * @param empleadoService servicio a establecer
     */
    public void setEmpleadoService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
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
