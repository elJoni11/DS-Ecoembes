package Ecoembes.service;

import Ecoembes.dto.AssemblerMethods;
import Ecoembes.dto.PlantaDTO;
import Ecoembes.entity.Planta;
import Ecoembes.repository.InMemoryDatabase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public Integer getCapacidadPlanta(String id, LocalDate fecha) {
        Planta p = db.plantas.get(id);
        if (p == null) {
            throw new RuntimeException("Planta no encontrada"); // 404
        }
        Integer capacidad = p.getCapacidadDeterminada().get(fecha);
        if (capacidad == null) {
            throw new RuntimeException("No hay datos de capacidad para la fecha seleccionada"); // 400
        }
        return capacidad;
    }
}