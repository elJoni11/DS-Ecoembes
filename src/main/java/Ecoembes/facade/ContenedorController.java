package Ecoembes.facade;

import Ecoembes.dto.ContenedorDTO;
import Ecoembes.dto.request.ActualizarContenedorRequestDTO;
import Ecoembes.dto.request.CrearContenedorRequestDTO;
import Ecoembes.entity.NivelLlenado;
import Ecoembes.service.ContenedorService;
import Ecoembes.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contenedor")
@Tag(name = "ContenedorController", description = "Gestión de los contenedores de reciclaje")
public class ContenedorController {

    private final ContenedorService contenedorService;
    private final LoginService loginService;

    public ContenedorController(ContenedorService contenedorService, LoginService loginService) {
        this.contenedorService = contenedorService;
        this.loginService = loginService;
    }

    @Operation(summary = "(Sensor) Actualización de estado del contenedor",
               description = "Invocada por el sensor para reportar su estado.")
    @ApiResponse(responseCode = "200", description = "Estado actualizado con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Contenedor no encontrado.", content = @Content)
    @PutMapping("/{id_contenedor}/estado")
    public ResponseEntity<ContenedorDTO> actualizarEstado(
            @PathVariable("id_contenedor") String id,
            @Valid @RequestBody ActualizarContenedorRequestDTO update) {
        // Este endpoint (simulación de sensor) no requiere token de empleado
        return ResponseEntity.ok(contenedorService.actualizarEstado(id, update));
    }

    @Operation(summary = "(Personal) Creación de un nuevo contenedor")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @ApiResponse(responseCode = "201", description = "Contenedor creado con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o ID duplicado.", content = @Content)
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @PostMapping("/crear")
    public ResponseEntity<ContenedorDTO> crearContenedor(
            @RequestParam("token") String token,
            @Valid @RequestBody CrearContenedorRequestDTO request) {
        loginService.validateAndGetUserEmail(token); // Valida el token
        ContenedorDTO nuevo = contenedorService.crearContenedor(request);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "(Personal) Descargar información de contenedores",
               description = "Descarga la información actualizada de todos los contenedores.")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @ApiResponse(responseCode = "200", description = "Lista de contenedores devuelta.")
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @GetMapping
    public ResponseEntity<List<ContenedorDTO>> descargarInfoContenedores(
            @RequestParam("token") String token) {
        loginService.validateAndGetUserEmail(token);
        return ResponseEntity.ok(contenedorService.getContenedores());
    }

    @Operation(summary = "(Personal) Consulta del historial de un contenedor",
               description = "Consulta el historial desde una fecha específica (YYYY-MM-DD).")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @ApiResponse(responseCode = "200", description = "Historial devuelto con éxito.")
    @ApiResponse(responseCode = "400", description = "Fecha inválida.", content = @Content)
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Contenedor no encontrado.", content = @Content)
    @GetMapping("/{contenedorID}/historial")
    public ResponseEntity<Map<LocalDate, NivelLlenado>> getHistorial(
            @RequestParam("token") String token,
            @PathVariable String contenedorID,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaConsulta) {
        loginService.validateAndGetUserEmail(token);
        Map<LocalDate, NivelLlenado> historial = contenedorService.getHistorialContenedor(contenedorID, fechaConsulta);
        return ResponseEntity.ok(historial);
    }
}