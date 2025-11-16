package Ecoembes.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja errores de autenticación (Token inválido, login incorrecto)
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException(SecurityException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    // Maneja errores de validación de DTOs (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "; ");
        return new ResponseEntity<>(Map.of("error", "Datos de entrada inválidos", "detalles", errors), HttpStatus.BAD_REQUEST);
    }

    // Maneja errores de negocio (ej. "No encontrado", "ID duplicado")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request) {
        String message = ex.getMessage().toLowerCase();
        
        // Error 404
        if (message.contains("no encontrado") || message.contains("not found")) {
            return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
        }
        
        // Error 400
        if (message.contains("inválido") || message.contains("duplicado") || message.contains("capacidad")) {
             return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        
        // Error genérico 500
        System.err.println("Error no controlado: " + ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(Map.of("error", "Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
