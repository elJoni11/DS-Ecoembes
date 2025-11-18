package Ecoembes.facade;

import Ecoembes.dto.PlantaDTO;
import Ecoembes.service.LoginService;
import Ecoembes.service.PlantaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "PlantaController", description = "Gestión de las plantas de reciclaje")
public class PlantaController {

    private final PlantaService plantaService;
    private final LoginService loginService;

    public PlantaController(PlantaService plantaService, LoginService loginService) {
        this.plantaService = plantaService;
        this.loginService = loginService;
    }

    @Operation(summary = "Consultar lista de plantas de reciclaje")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @ApiResponse(responseCode = "200", description = "Lista de plantas devuelta.")
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @GetMapping("/plantas")
    public ResponseEntity<List<PlantaDTO>> getPlantas(@RequestParam("token") String token) {
        loginService.validateAndGetUserEmail(token);
        return ResponseEntity.ok(plantaService.getAllPlantas());
    }

    @Operation(summary = "Consultar capacidad disponible de una planta",
               description = "Consulta la capacidad (en toneladas) para un día específico (YYYY-MM-DD).")
    @Parameter(in = ParameterIn.QUERY, name = "token", required = true)
    @Parameter(in = ParameterIn.PATH, name = "plantaID", required = true)
    @ApiResponse(responseCode = "200", description = "Capacidad devuelta.")
    @ApiResponse(responseCode = "400", description = "Fecha inválida.", content = @Content)
    @ApiResponse(responseCode = "401", description = "Token inválido.", content = @Content)
    @ApiResponse(responseCode = "404", description = "Planta no encontrada.", content = @Content)
    @GetMapping("/planta/{plantaID}/capacidad")
    public ResponseEntity<Map<String, Integer>> getCapacidadPlanta(
            @RequestParam("token") String token,
            @PathVariable String plantaID,
            @RequestParam (name = "fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        loginService.validateAndGetUserEmail(token);
        Integer capacidad = plantaService.getCapacidadPlanta(plantaID, fecha);
        return ResponseEntity.ok(Map.of("capacidad", capacidad));
    }
}