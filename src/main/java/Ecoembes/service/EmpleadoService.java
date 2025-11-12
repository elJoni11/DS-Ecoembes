package Ecoembes.service;

import java.util.HashMap;
import java.util.Map;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import Ecoembes.entity.Empleado;

public class EmpleadoService {

    // --- (1) Simulación de la "Base de Datos" en memoria (Prototipo 1) ---
    // Repositorio de todos los empleados
    private final Map<Long, Empleado> repositorioEmpleados;
    
    // --- (2) Gestión del Estado/Token (Patrón StateManagement) ---
    // Clave: Token (String), Valor: ID de Empleado (Long)
    // Se usa ConcurrentHashMap para ser thread-safe, mejor para servicios.
    private final Map<String, Long> tokensActivos;

    // --- Constructor ---
    public EmpleadoService() {
        this.repositorioEmpleados = new HashMap<>();
        this.tokensActivos = new ConcurrentHashMap<>();
        inicializarDatosDemo(); 
    }
    
    // Método de inicialización (Solo para Prototipo 1)
    private void inicializarDatosDemo() {
        // ID 1: Administrador (usado para la creación de contenedores)
        repositorioEmpleados.put(1L, new Empleado(1L, "Ana García", "ana@ecoembes.es", "pass123"));
        // ID 2: Operario (otro perfil para la demo)
        repositorioEmpleados.put(2L, new Empleado(2L, "Juan Perez", "juan@ecoembes.es", "pass456"));
    }


    // --- (3) Funcionalidad de iniciarSesion ---
    /**
     * Realiza el proceso de autenticación. Genera y retorna un token si es exitoso.
     * @param email El correo electrónico del empleado.
     * @param password La contraseña del empleado.
     * @return El token generado (String) o null si las credenciales son incorrectas.
     */
    public String iniciarSesion(String email, String password) {
        // 1. Buscar el empleado por email (simulación de búsqueda en DB)
        Empleado empleado = repositorioEmpleados.values().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (empleado == null) {
            return null; // Empleado no encontrado
        }

        // 2. Verificar la contraseña (¡ATENCIÓN! Simulación sin hashing)
        if (!empleado.getPassword().equals(password)) {
            return null; // Contraseña incorrecta
        }
        
        // 3. Generar el token (timestamp del momento)
        // Se usa el timestamp como un token simple para P1.
        String token = String.valueOf(Instant.now().toEpochMilli());
        
        // 4. Almacenar el token activo
        tokensActivos.put(token, empleado.getId());
        
        return token;
    }

    // --- (4) Funcionalidad de cerrarSesion ---
    /**
     * Cierra la sesión eliminando el token activo.
     * @param token El token a invalidar.
     * @return true si el token fue eliminado, false si no existía.
     */
    public boolean cerrarSesion(String token) {
        // Eliminar el token del estado del servidor
        return tokensActivos.remove(token) != null; 
    }
    
    // --- (5) Método de Utilidad para la validación (usado por la Façade) ---
    /**
     * Verifica si un token es válido y retorna el ID del empleado.
     * @param token El token a verificar.
     * @return El ID del empleado Long, o null si el token no es válido/ha caducado.
     */
    public Long validarToken(String token) {
        // En P1, solo se verifica si el token existe en el mapa.
        return tokensActivos.get(token);
    }
    
    // --- (6) Método de Utilidad para obtener el objeto Empleado completo ---
    public Empleado getEmpleadoById(Long id) {
        return repositorioEmpleados.get(id);
    }
}