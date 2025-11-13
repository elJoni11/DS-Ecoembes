package Ecoembes.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de plantas de reciclaje
 */
@Service
public class PlantaService {
    
    // Simulación de base de datos de plantas (plantaID -> capacidad disponible)
    private Map<String, Integer> capacidadPlantas = new HashMap<>();
    
    public PlantaService() {
        // Inicializar con algunas plantas de ejemplo
        inicializarPlantas();
    }
    
    private void inicializarPlantas() {
        capacidadPlantas.put("PLANTA-001", 500);
        capacidadPlantas.put("PLANTA-002", 750);
        capacidadPlantas.put("PLANTA-003", 1000);
        capacidadPlantas.put("PLANTA-004", 300);
    }
    
    /**
     * Obtiene la capacidad disponible de una planta
     * @param plantaID ID de la planta
     * @return capacidad disponible (número de contenedores que puede procesar)
     */
    public int getCapacidad(String plantaID) {
        Integer capacidad = capacidadPlantas.get(plantaID);
        return capacidad != null ? capacidad : 0;
    }
    
    /**
     * Reduce la capacidad de una planta (cuando se asigna un contenedor)
     */
    public void reducirCapacidad(String plantaID, int cantidad) {
        Integer capacidadActual = capacidadPlantas.get(plantaID);
        if (capacidadActual != null) {
            int nuevaCapacidad = Math.max(0, capacidadActual - cantidad);
            capacidadPlantas.put(plantaID, nuevaCapacidad);
        }
    }
    
    /**
     * Aumenta la capacidad de una planta (cuando se procesa un contenedor)
     */
    public void aumentarCapacidad(String plantaID, int cantidad) {
        Integer capacidadActual = capacidadPlantas.get(plantaID);
        if (capacidadActual != null) {
            capacidadPlantas.put(plantaID, capacidadActual + cantidad);
        }
    }
    
    /**
     * Verifica si una planta tiene capacidad suficiente
     */
    public boolean tieneCapacidadSuficiente(String plantaID, int cantidadRequerida) {
        return getCapacidad(plantaID) >= cantidadRequerida;
    }
    
    /**
     * Obtiene todas las plantas con sus capacidades
     */
    public Map<String, Integer> getAllPlantas() {
        return new HashMap<>(capacidadPlantas);
    }
}
