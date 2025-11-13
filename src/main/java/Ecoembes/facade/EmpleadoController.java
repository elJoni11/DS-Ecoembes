package Ecoembes.facade;

import Ecoembes.service.EmpleadoService;
import Ecoembes.service.LoginService;
import Ecoembes.dto.EmpleadoDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gestión de empleados (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de empleados
 */
@RestController
@RequestMapping("/api/empleados")
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
    @PostMapping("/iniciarSesion")
    public String IniciarSesion(@RequestBody EmpleadoCredenciales credenciales) {
        
		String email = credenciales.getEmail();
		String password = credenciales.getPassword();
    	
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
    @PostMapping("/cerrarSesion")
    public void CerrarSesion(@RequestHeader("X-Auth-Token") String token) {
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
     * Obtiene información de un empleado por ID
     * @param id ID del empleado
     * @param token token de sesión para validación
     * @return EmpleadoDTO con la información del empleado
     */
    @GetMapping("/porId")
    public EmpleadoDTO getEmpleadoById(@PathVariable Long id, @RequestHeader("X-Auth-Token") String token) {
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return empleadoService.getEmpleadoById(id);
    }
    
    /**
     * Obtiene información de un empleado por email
     * @param email email del empleado
     * @param token token de sesión para validación
     * @return EmpleadoDTO con la información del empleado
     */
    @GetMapping("/porEmail")
    public EmpleadoDTO getEmpleadoByEmail(@PathVariable String email, @RequestHeader("X-Auth-Token") String token) {
        // Validar sesión
        if (!loginService.validarToken(token)) {
            throw new SecurityException("Token inválido o expirado");
        }
        
        return empleadoService.getEmpleadoByEmail(email);
    }
    
 // Clase interna auxiliar para el login, necesaria para recibir el JSON
    private static class EmpleadoCredenciales {
        private String email;
        private String password;
        
        // Getters y setters necesarios para que Spring lea el JSON
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
