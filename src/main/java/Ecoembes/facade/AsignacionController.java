package Ecoembes.facade;

import Ecoembes.dto.AsignacionDTO;
import Ecoembes.dto.request.AsignacionRequestDTO;
import Ecoembes.service.AsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asignacion")
@Tag(name = "AsignacionController", description = "Asignación de contenedores a plantas")
public class AsignacionController {

    private final AsignacionService asignacionService;

    /** Constructor con inyección de dependencias **/
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @Operation(summary = "Asignación de contenedores a una planta",
               description = "Asigna contenedores, registra auditoría y notifica a la planta.")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @ApiResponse(responseCode = "201", description = "Asignación creada con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos inválidos.", content = @Content)
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Planta o contenedor no encontrado.", content = @Content)
    @PostMapping
    public ResponseEntity<AsignacionDTO> asignarContenedores(
            @RequestParam("token") String token,
            @Valid @RequestBody AsignacionRequestDTO request) {
        AsignacionDTO asignacion = asignacionService.asignarContenedores(token, request);
        return new ResponseEntity<>(asignacion, HttpStatus.CREATED);
    }
}