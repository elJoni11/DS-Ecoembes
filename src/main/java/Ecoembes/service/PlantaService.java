package Ecoembes.service;

import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.PlantaDTO;
import Ecoembes.entity.Planta;
import Ecoembes.repository.InMemoryDatabase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantaService {

    private final InMemoryDatabase db;

    public PlantaService(InMemoryDatabase db) {
        this.db = db;
    }

    public List<PlantaDTO> getAllPlantas() {
        return db.plantas.values().stream()
                .map(AssemblerMethods::toPlantaDTO)
                .collect(Collectors.toList());
    }

    public Integer getCapacidadPlanta(String id, String fechaString) {
        Planta p = db.plantas.get(id);
        if (p == null) {
            throw new RuntimeException("Planta no encontrada: " + id);
        }
        
        LocalDate fecha = LocalDate.parse(fechaString, DateTimeFormatter.ISO_DATE);
        
        // --- 🔍 ZONA DE DEPURACIÓN (MIRA LA CONSOLA DE ECLIPSE) ---
        System.out.println("\n========== DEBUG PLANTA SERVICE ==========");
        System.out.println("1. Planta encontrada: " + p.getNombre() + " (ID: " + id + ")");
        System.out.println("2. Fecha que tú pides: " + fecha);
        System.out.println("3. Contenido de la base de datos para esta planta:");
        
        if (p.getCapacidadDeterminada().isEmpty()) {
            System.err.println("   ❌ ¡EL MAPA DE CAPACIDAD ESTÁ VACÍO! DataInitializer no ha cargado datos.");
        } else {
            p.getCapacidadDeterminada().forEach((k, v) -> {
                System.out.println("   -> Disponible: " + k + " | Capacidad: " + v);
                // Comprobación extra
                if (k.isEqual(fecha)) {
                    System.out.println("      ✅ ¡COINCIDENCIA ENCONTRADA AQUÍ!");
                }
            });
        }
        System.out.println("==========================================\n");
        // -----------------------------------------------------------

        Optional<Map.Entry<LocalDate, Integer>> entry = p.getCapacidadDeterminada().entrySet().stream()
                .filter(e -> e.getKey().isEqual(fecha))
                .findFirst();

        if (entry.isEmpty()) {
            throw new IllegalArgumentException("No hay datos de capacidad para la fecha: " + fechaString);
        }
        
        return entry.get().getValue();
    }
}