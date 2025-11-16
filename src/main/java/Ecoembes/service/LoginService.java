package Ecoembes.service;

import Ecoembes.entity.Empleado;
import Ecoembes.repository.InMemoryDatabase;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final InMemoryDatabase db;

    public LoginService(InMemoryDatabase db) {
        this.db = db;
    }

    public String autenticar(String email, String password) {
        Empleado empleado = db.empleados.get(email);
        if (empleado != null && empleado.getPassword().equals(password)) {
            String token = String.valueOf(System.currentTimeMillis());
            db.activeTokens.put(token, email);
            return token;
        }
        throw new SecurityException("Credenciales inválidas");
    }

    public void cerrarSesion(String token) {
        if (db.activeTokens.remove(token) == null) {
            throw new SecurityException("Token inválido o sesión ya cerrada");
        }
    }

    public String validateAndGetUserEmail(String token) {
        if (token == null || token.isEmpty()) {
            throw new SecurityException("Token no proporcionado");
        }
        String email = db.activeTokens.get(token);
        if (email == null) {
            throw new SecurityException("Token no válido o caducado. Por favor, inicie sesión.");
        }
        return email;
    }
}