package Ecoembes.service;

import Ecoembes.dto.EmpleadoDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de empleados y autenticación
 */
@Service
public class EmpleadoService {
    
    // Simulación de base de datos de empleados
    private Map<String, EmpleadoDTO> empleados = new HashMap<>();
    
    // Sesiones activas (email -> token)
    private Map<String, String> sesionesActivas = new HashMap<>();
    
    public EmpleadoService() {
        // Inicializar con algunos empleados de prueba
        inicializarEmpleados();
    }
    
    private void inicializarEmpleados() {
        EmpleadoDTO emp1 = new EmpleadoDTO(1L, "Juan Pérez", "juan@ecoembes.com", "admin123");
        EmpleadoDTO emp2 = new EmpleadoDTO(2L, "María García", "maria@ecoembes.com", "user123");
        
        empleados.put(emp1.getEmail(), emp1);
        empleados.put(emp2.getEmail(), emp2);
    }
    
    /**
     * Inicia sesión de un empleado
     * @param email email del empleado
     * @param password contraseña del empleado
     * @return EmpleadoDTO si las credenciales son correctas, null en caso contrario
     */
    public EmpleadoDTO iniciarSesion(String email, String password) {
        EmpleadoDTO empleado = empleados.get(email);
        
        if (empleado != null && empleado.getPassword().equals(password)) {
            // Credenciales correctas
            return empleado;
        }
        
        // Credenciales incorrectas
        return null;
    }
    
    /**
     * Cierra la sesión de un empleado
     * @param token token de sesión a cerrar
     */
    public void cerrarSesion(String token) {
        // Buscar y eliminar la sesión
        sesionesActivas.values().remove(token);
    }
    
    /**
     * Registra una sesión activa
     */
    public void registrarSesion(String email, String token) {
        sesionesActivas.put(email, token);
    }
    
    /**
     * Obtiene un empleado por email
     */
    public EmpleadoDTO getEmpleadoByEmail(String email) {
        return empleados.get(email);
    }
    
    /**
     * Obtiene un empleado por ID
     */
    public EmpleadoDTO getEmpleadoById(Long id) {
        return empleados.values().stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}
