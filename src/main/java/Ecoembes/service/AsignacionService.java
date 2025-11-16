package Ecoembes.service;

import Ecoembes.dto.AsignacionDTO;
import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.request.AsignacionRequestDTO;
import Ecoembes.entity.*;
import Ecoembes.repository.InMemoryDatabase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AsignacionService {

    private final InMemoryDatabase db;
    private final LoginService loginService;

    public AsignacionService(InMemoryDatabase db, LoginService loginService) {
        this.db = db;
        this.loginService = loginService;
    }

    public AsignacionDTO asignarContenedores(String token, AsignacionRequestDTO request) {
        // 1. Auditoría: Validar token y obtener email
        String asignador = loginService.validateAndGetUserEmail(token);

        // 2. Validar que la planta existe
        Planta planta = db.plantas.get(request.getPlantaID());
        if (planta == null) {
            throw new RuntimeException("Planta no encontrada: " + request.getPlantaID()); // 404
        }
        
        // 3. Recopilar contenedores y calcular total
        List<Contenedor> contenedoresAsignados = new ArrayList<>();
        int totalEnvases = 0;
        
        for (String id : request.getListaContenedoresID()) {
            Contenedor c = db.contenedores.get(id);
            if (c == null) {
                throw new RuntimeException("Contenedor no encontrado: " + id); // 404
            }
            contenedoresAsignados.add(c);
            totalEnvases += c.getEnvasesEstimados();
            
            // Simulación: Reseteamos el contenedor tras la asignación
            c.setEnvasesEstimados(0);
            c.setNivelLlenado(NivelLlenado.VERDE);
        }

        // 4. Crear la entidad Asignacion
        Asignacion asignacion = new Asignacion();
        asignacion.setAsignacionID(UUID.randomUUID().toString());
        asignacion.setPlantaID(request.getPlantaID());
        asignacion.setFechaPrevista(request.getFechaPrevista());
        asignacion.setListaContenedores(contenedoresAsignados);
        asignacion.setTotalEnvasesEstimados(totalEnvases);
        asignacion.setAsignador(asignador); // Auditoría

        // 5. Simular la notificación a la planta
        simularNotificacionPlanta(planta.getNombre(), contenedoresAsignados.size(), totalEnvases);
        asignacion.setNotificacion(true);

        // 6. Guardar la asignación (en memoria)
        db.asignaciones.add(asignacion);

        // 7. Devolver el DTO
        return AssemblerMethods.toAsignacionDTO(asignacion);
    }

    private void simularNotificacionPlanta(String nombrePlanta, int numContenedores, int totalEnvases) {
        System.out.println("--- SIMULACIÓN DE NOTIFICACIÓN ---");
        System.out.printf("Destino: Planta '%s'%n", nombrePlanta);
        System.out.printf("Contenedores asignados: %d%n", numContenedores);
        System.out.printf("Cantidad total estimada (envases): %d%n", totalEnvases);
        System.out.println("------------------------------------");
    }
}
