package com.deusto.ecoembes.appService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// Importamos la clase de dominio Planta
	import com.deusto.ecoembes.entity.Planta;

public class PlantaService {

    // --- (1) Simulación de la "Base de Datos" en memoria (Prototipo 1) ---
    // Clave: Planta ID (String), Valor: Objeto Planta
    private final Map<String, Planta> repositorioPlantas;
    
    // --- Constructor ---
    public PlantaService() {
        this.repositorioPlantas = new HashMap<>();
        inicializarDatosDemo(); 
    }
    
    // Método de inicialización (Solo para Prototipo 1)
    private void inicializarDatosDemo() {
        LocalDate hoy = LocalDate.now();
        
        // Planta 1: Alta Capacidad, Disponible
        Planta p1 = new Planta("P500A", "EcoRecicla Madrid Norte", "C/ Reciclaje 1");
        p1.actualizarCapacidad(hoy, 5000);   // 5000 kg hoy
        p1.actualizarCapacidad(hoy.plusDays(1), 5000);
        
        // Planta 2: Capacidad Limitada, Casi llena
        Planta p2 = new Planta("P501B", "PlasSB Ltd.", "Av. Plástico 2");
        p2.actualizarCapacidad(hoy, 1500);   // 1500 kg hoy
        p2.actualizarCapacidad(hoy.plusDays(1), 1000);

        repositorioPlantas.put(p1.getPlantaID(), p1);
        repositorioPlantas.put(p2.getPlantaID(), p2);
        
        // Simulación: La capacidad de P2 ya fue utilizada hoy, digamos que se asignaron 1000kg.
        // En un servicio real, esto se actualizaría aquí. Pero para P1, solo devolvemos la inicial.
    }


    // --- (2) Funcionalidad de Consulta de Capacidad (Ajustada) ---
    /**
     * Consulta la capacidad restante para una planta en una fecha específica.
     * @param plantaID El ID de la planta.
     * @param fecha La fecha para la que se consulta la capacidad.
     * @return La capacidad restante en toneladas (Integer).
     */
    public Integer getCapacidadParaFecha(String plantaID, LocalDate fecha) {
        Planta planta = repositorioPlantas.get(plantaID);
        
        if (planta != null) {
            // El método de la clase Planta se encarga de devolver 0 si no encuentra la fecha.
            return planta.getCapacidadParaFecha(fecha);
        }
        return 0; // Si la planta no existe, la capacidad es 0.
    }
    
    // --- (3) Funcionalidad Clave: Verificar y Reservar Capacidad ---
    /**
     * Verifica si la planta tiene suficiente capacidad para la cantidad de envases en la fecha dada.
     * Este método es crucial para la AsignacionService.
     * @param plantaID El ID de la planta.
     * @param envasesTotal La cantidad de envases estimados (o su peso equivalente en kg/toneladas).
     * @param fecha La fecha de la asignación/entrega.
     * @return true si la capacidad es suficiente, false si no.
     */
    public boolean tieneCapacidadSuficiente(String plantaID, int envasesTotal, LocalDate fecha) {
        int capacidadDisponible = getCapacidadParaFecha(plantaID, fecha);
        
        // Aquí se necesitaría una conversión de envases a kg/toneladas (ej: 1000 envases = 100kg).
        // Por ahora, asumiremos que 'envasesTotal' está en las mismas unidades que 'capacidadDisponible'.
        
        return envasesTotal <= capacidadDisponible;
    }
    
    // --- (4) Funcionalidad de los 'Servicios Externos' (PlasSB y ContSocket) ---
    /**
     * Actualiza la capacidad de la planta para un día específico (simulando la recepción de datos).
     * @param plantaID El ID de la planta.
     * @param fecha La fecha a actualizar.
     * @param nuevaCapacidad La nueva capacidad total disponible.
     * @return true si se actualizó, false si la planta no existe.
     */
    public boolean actualizarCapacidadDiaria(String plantaID, LocalDate fecha, int nuevaCapacidad) {
        Planta planta = repositorioPlantas.get(plantaID);
        if (planta != null) {
            planta.actualizarCapacidad(fecha, nuevaCapacidad);
            return true;
        }
        return false;
    }
    
    // --- (5) Métodos de Utilidad ---
    public Planta getPlantaById(String id) {
        return repositorioPlantas.get(id);
    }
    
    public List<Planta> getAllPlantas() {
        return new ArrayList<>(repositorioPlantas.values());
    }
}