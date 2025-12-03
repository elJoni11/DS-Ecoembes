package Ecoembes.service;

import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.ContenedorDTO;
import Ecoembes.dto.request.CrearContenedorRequestDTO;
import Ecoembes.dto.request.ActualizarContenedorRequestDTO;
import Ecoembes.entity.Contenedor;
import Ecoembes.entity.NivelLlenado;
import Ecoembes.repository.ContenedorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContenedorService {

	private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }

    public ContenedorDTO actualizarEstado(String id, ActualizarContenedorRequestDTO update) {
        Contenedor c = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado")); // 404

        c.setFechaConsulta(LocalDateTime.now());
        c.setEnvasesEstimados(update.getEnvasesEstimados());
        c.setNivelLlenado(update.getNivelLlenado());
        c.getHistorico().put(LocalDate.now(), update.getNivelLlenado());
        
        // Guardamos en BD
        contenedorRepository.save(c);
        
        System.out.printf("[SENSOR] Contenedor %s actualizado: Nivel %s%n", id, update.getNivelLlenado());
        return AssemblerMethods.toContenedorDTO(c);
    }

    public ContenedorDTO crearContenedor(CrearContenedorRequestDTO request) {
        if (contenedorRepository.existsById(request.getContenedorId())) {
            throw new RuntimeException("ID de contenedor duplicado"); // Será 400
        }
        Contenedor contenedor = new Contenedor();
        contenedor.setContenedorId(request.getContenedorId());
        contenedor.setUbicacion(request.getUbicacion());
        contenedor.setCapacidad(request.getCapacidad());
        contenedor.setNivelLlenado(NivelLlenado.VERDE);
        contenedor.setEnvasesEstimados(0);
        contenedor.setFechaConsulta(LocalDateTime.now());
        
        // Guardamos en BD
        contenedorRepository.save(contenedor);
        
        return AssemblerMethods.toContenedorDTO(contenedor);
    }

    public List<ContenedorDTO> getContenedores() {
        return contenedorRepository.findAll().stream()
                .map(AssemblerMethods::toContenedorDTO)
                .collect(Collectors.toList());
    }

    public Map<LocalDate, NivelLlenado> getHistorialContenedor(String id, String fechaString) {
        Contenedor c = contenedorRepository.findById(id)
        		.orElseThrow(() -> new RuntimeException("Contenedor no encontrado")); // 404
        if (c == null) {
            throw new RuntimeException("Contenedor no encontrado"); // Será 404
        }
        
        LocalDate fechaConsulta = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);
        Map<LocalDate, NivelLlenado> historialFiltrado = c.getHistorico().entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(fechaConsulta))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (historialFiltrado.isEmpty()) {
             throw new IllegalArgumentException("Historial no encontrado para la fecha inicial: " + fechaConsulta);
        }
        
        return historialFiltrado;
    }
}