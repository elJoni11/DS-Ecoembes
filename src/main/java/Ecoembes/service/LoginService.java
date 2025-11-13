package Ecoembes.service;

import Ecoembes.dto.EmpleadoDTO;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de tokens de autenticación
 */
@Service
public class LoginService {
    
    // Almacén de tokens activos (token -> información del empleado)
    private Map<String, TokenInfo> tokensActivos = new HashMap<>();
    
    // Tiempo de expiración de tokens en minutos
    private static final int EXPIRACION_MINUTOS = 60;
    
    /**
     * Clase interna para almacenar información del token
     */
    private static class TokenInfo {
        Long empleadoId;
        LocalDateTime fechaCreacion;
        
        TokenInfo(Long empleadoId, LocalDateTime fechaCreacion) {
            this.empleadoId = empleadoId;
            this.fechaCreacion = fechaCreacion;
        }
        
        boolean estaExpirado() {
            return LocalDateTime.now().isAfter(fechaCreacion.plusMinutes(EXPIRACION_MINUTOS));
        }
    }
    
    /**
     * Genera un token de sesión para un empleado
     * @param empleado empleado para el cual generar el token
     * @return token de sesión generado
     */
    public String generarToken(EmpleadoDTO empleado) {
        // Generar un token único
        String token = "TOKEN-" + UUID.randomUUID().toString();
        
        // Almacenar el token con la información del empleado
        tokensActivos.put(token, new TokenInfo(empleado.getId(), LocalDateTime.now()));
        
        return token;
    }
    
    /**
     * Valida si un token es válido y no ha expirado
     * @param token token a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean validarToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        
        TokenInfo info = tokensActivos.get(token);
        
        if (info == null) {
            return false;
        }
        
        // Verificar si el token ha expirado
        if (info.estaExpirado()) {
            tokensActivos.remove(token);
            return false;
        }
        
        return true;
    }
    
    /**
     * Invalida un token (lo elimina del sistema)
     */
    public void invalidarToken(String token) {
        tokensActivos.remove(token);
    }
    
    /**
     * Obtiene el ID del empleado asociado a un token
     */
    public Long getEmpleadoIdFromToken(String token) {
        TokenInfo info = tokensActivos.get(token);
        return info != null ? info.empleadoId : null;
    }
    
    /**
     * Limpia tokens expirados (método de mantenimiento)
     */
    public void limpiarTokensExpirados() {
        tokensActivos.entrySet().removeIf(entry -> entry.getValue().estaExpirado());
    }
}
