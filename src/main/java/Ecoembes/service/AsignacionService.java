package Ecoembes.service;

import Ecoembes.dto.AsignacionDTO;
import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.request.AsignacionRequestDTO;
import Ecoembes.entity.*;
import Ecoembes.repository.AsignacionRepository;
import Ecoembes.repository.ContenedorRepository;
import Ecoembes.repository.PlantaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AsignacionService {

	private final AsignacionRepository asignacionRepository;
    private final ContenedorRepository contenedorRepository;
    private final PlantaRepository plantaRepository;
    private final LoginService loginService;

    public AsignacionService(AsignacionRepository asignacionRepository, 
            ContenedorRepository contenedorRepository,
            PlantaRepository plantaRepository,
            LoginService loginService) {
		this.asignacionRepository = asignacionRepository;
		this.contenedorRepository = contenedorRepository;
		this.plantaRepository = plantaRepository;
		this.loginService = loginService;
    }

    public AsignacionDTO asignarContenedores(String token, AsignacionRequestDTO request) {
        // 1. Auditoría: Validar token y obtener email
        String asignador = loginService.validateAndGetUserEmail(token);

        // 2. Validar que la planta existe
        if (!plantaRepository.existsById(request.getPlantaID())) {
            throw new RuntimeException("Planta no encontrada: " + request.getPlantaID());
        }
        Planta planta = plantaRepository.findById(request.getPlantaID()).get();
        
        // 3. Recuperar y validar contenedores
        List<Contenedor> contenedoresAsignados = new ArrayList<>();
        int totalEnvases = 0;
        
        for (String id : request.getListaContenedoresID()) {
            Contenedor c = contenedorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Contenedor no encontrado: " + id));
            
            contenedoresAsignados.add(c);
            totalEnvases += c.getEnvasesEstimados();
            
            // Resetear y guardar contenedor
            c.setEnvasesEstimados(0);
            c.setNivelLlenado(NivelLlenado.VERDE);
            contenedorRepository.save(c);
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

        // 6. Guardar en BBDD
        asignacionRepository.save(asignacion);

        return AssemblerMethods.toAsignacionDTO(asignacion);
    }

    private void simularNotificacionPlanta(String nombrePlanta, int numContenedores, int totalEnvases) {
        System.out.println("--- NOTIFICACIÓN (JPA) ---");
        System.out.printf("Destino: Planta '%s'%n", nombrePlanta);
        System.out.printf("Contenedores asignados: %d%n", numContenedores);
        System.out.printf("Cantidad total estimada (envases): %d%n", totalEnvases);
        System.out.println("------------------------------------");
    }
}
