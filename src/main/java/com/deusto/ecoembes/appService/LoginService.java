package com.deusto.ecoembes.appService;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginService {

    // El atributo es 'tokensActivos', pero el tipo de clave/valor es más importante.
    // Clave: Token (String), Valor: ID de Empleado (Long).
    // Usamos ConcurrentHashMap para ser seguros en un entorno multihilo (servidor).
    private final Map<String, Long> tokensActivos;

    // --- Constructor ---
    public LoginService() {
        this.tokensActivos = new ConcurrentHashMap<>();
    }

    // --- Funcionalidad de Generación de Token ---
    /**
     * Genera un nuevo token único y lo asocia al ID del empleado.
     * Esta función debe ser llamada por EmpleadoAppService después de una autenticación exitosa.
     * @param empleadoId El ID del empleado que inicia sesión.
     * @return El token de sesión generado.
     */
    public String generarToken(Long empleadoId) {
        // Genera un token simple basado en el timestamp actual.
        // En una aplicación real, se usaría un JWT o un UUID para mayor seguridad.
        String token = String.valueOf(Instant.now().toEpochMilli()) + "-" + empleadoId;

        // Asocia el token generado con el ID del empleado.
        tokensActivos.put(token, empleadoId);
        
        return token;
    }

    // --- Funcionalidad de Validación de Token ---
    /**
     * Verifica si un token es válido y retorna el ID del empleado asociado.
     * La Façade usará este método para asegurar que cualquier petición es autorizada.
     * @param token El token recibido en la cabecera de la petición (ej: Authorization).
     * @return El ID del empleado (Long) si el token es válido, o null si no lo es.
     */
    public Long validarToken(String token) {
        // En P1, solo se verifica si el token existe en el mapa de activos.
        return tokensActivos.get(token);
    }
    
    // --- Funcionalidad de Eliminación de Token (Logout) ---
    /**
     * Invalida un token para cerrar la sesión.
     * @param token El token a eliminar.
     * @return true si el token fue removido, false si no existía.
     */
    public boolean eliminarToken(String token) {
        return tokensActivos.remove(token) != null;
    }
    
    // --- Funcionalidad de Utilidad ---
    public int getNumeroTokensActivos() {
        return tokensActivos.size();
    }
}