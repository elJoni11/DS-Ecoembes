package Ecoembes.facade;

import Ecoembes.dto.request.LoginRequestDTO;
import Ecoembes.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Controller para gestión de login y tokens (Patrón Facade)
 * Proporciona una interfaz simplificada para las operaciones de autenticación
 **/
@RestController
@RequestMapping("/api/login")
@Tag(name = "LoginController", description = "Gestión de la sesión del personal de Ecoembes")
public class LoginController {
    
    private LoginService loginService;
    
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    
    @Operation(summary = "Inicio de sesión del empleado",
            description = "Autentica y genera un token de sesión (timestamp).")
	 @ApiResponse(responseCode = "200", description = "Login correcto. Devuelve el TOKEN.")
	 @ApiResponse(responseCode = "401", description = "Credenciales inválidas.", content = @Content)
	 @PostMapping("/iniciarSesion")
	 public ResponseEntity<Map<String, String>> iniciarSesion(@Valid @RequestBody LoginRequestDTO loginRequest) {
	     String token = loginService.autenticar(loginRequest.getEmail(), loginRequest.getPassword());
	     return ResponseEntity.ok(Map.of("token", token));
	 }
	
	 @Operation(summary = "Cierre de sesión del empleado",
	            description = "Invalida el token de sesión actual.")
	 @Parameter(in = ParameterIn.QUERY, name = "token", required = true, description = "Token de sesión")
	 @ApiResponse(responseCode = "204", description = "Logout correcto.")
	 @ApiResponse(responseCode = "401", description = "Token inválido o no proporcionado.", content = @Content)
	 @PostMapping("/cerrarSesion")
	 public ResponseEntity<Void> cerrarSesion(@RequestParam("token") String token) {
	     loginService.cerrarSesion(token);
	     return ResponseEntity.noContent().build();
	 }
}
