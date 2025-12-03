package Ecoembes.service;

import Ecoembes.entity.Empleado;
import Ecoembes.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {

    private final EmpleadoRepository empleadoRepository;
    
 // Mantenemos los tokens en memoria (Estado del Servidor) como pide el enunciado
    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();

    public LoginService(EmpleadoRepository empleadoRepository) {
    	this.empleadoRepository = empleadoRepository;
    }

    public String autenticar(String email, String password) {
    	// Buscamos en la Base de Datos H2
    	Empleado empleado = empleadoRepository.findById(email).orElse(null);
        if (empleado != null && empleado.getPassword().equals(password)) {
            String token = String.valueOf(System.currentTimeMillis());
            activeTokens.put(token, email);
            return token;
        }
        throw new SecurityException("Credenciales inválidas");
    }

    public void cerrarSesion(String token) {
        if (activeTokens.remove(token) == null) {
            throw new SecurityException("Token inválido o sesión ya cerrada");
        }
    }

    public String validateAndGetUserEmail(String token) {
        if (token == null || token.isEmpty()) {
            throw new SecurityException("Token no proporcionado");
        }
        String email = activeTokens.get(token);
        if (email == null) {
            throw new SecurityException("Token no válido o caducado. Por favor, inicie sesión.");
        }
        return email;
    }
}