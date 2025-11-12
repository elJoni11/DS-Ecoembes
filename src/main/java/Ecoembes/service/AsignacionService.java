package Ecoembes.service;

import Ecoembes.dto.AsignacionDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio para la gestión de asignaciones de contenedores a plantas
 */
public class AsignacionService {
    
    // Simulación de base de datos de asignaciones
    private Map<String, AsignacionDTO> asignaciones = new HashMap<>();
    private int contadorAsignaciones = 1;
    
    /**
     * Asigna un contenedor a una planta
     * @param contenedorID ID del contenedor
     * @param plantaID ID de la planta
     * @return AsignacionDTO con los datos de la asignación creada
     */
    public AsignacionDTO asignarContenedor(String contenedorID, String plantaID) {
        // Crear nueva asignación
        AsignacionDTO asignacion = new AsignacionDTO();
        asignacion.setAsignacionID("ASIG-" + String.format("%04d", contadorAsignaciones++));
        asignacion.setFecha(LocalDate.now());
        asignacion.setPlantaID(plantaID);
        
        // Crear lista con el contenedor asignado
        List<String> contenedores = new ArrayList<>();
        contenedores.add(contenedorID);
        asignacion.setContenedorID(contenedores);
        
        // Establecer notificación por defecto
        asignacion.setNotificacion("Contenedor " + contenedorID + " asignado a planta " + plantaID);
        
        // Guardar en la "base de datos"
        asignaciones.put(asignacion.getAsignacionID(), asignacion);
        
        return asignacion;
    }
    
    /**
     * Asigna múltiples contenedores a una planta
     * @param contenedoresIDs lista de IDs de contenedores
     * @param plantaID ID de la planta
     * @return AsignacionDTO con los datos de la asignación creada
     */
    public AsignacionDTO asignarContenedores(List<String> contenedoresIDs, String plantaID) {
        AsignacionDTO asignacion = new AsignacionDTO();
        asignacion.setAsignacionID("ASIG-" + String.format("%04d", contadorAsignaciones++));
        asignacion.setFecha(LocalDate.now());
        asignacion.setPlantaID(plantaID);
        asignacion.setContenedorID(new ArrayList<>(contenedoresIDs));
        
        asignacion.setNotificacion(contenedoresIDs.size() + " contenedores asignados a planta " + plantaID);
        
        asignaciones.put(asignacion.getAsignacionID(), asignacion);
        
        return asignacion;
    }
    
    /**
     * Envía una notificación sobre una asignación
     * @param asignacion asignación sobre la cual notificar
     */
    public void enviarNotificacion(AsignacionDTO asignacion) {
        // En un sistema real, esto enviaría un email, SMS, o notificación push
        System.out.println("═══════════════════════════════════════════");
        System.out.println("📧 NOTIFICACIÓN ENVIADA");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Asignación ID: " + asignacion.getAsignacionID());
        System.out.println("Fecha: " + asignacion.getFecha());
        System.out.println("Planta: " + asignacion.getPlantaID());
        System.out.println("Contenedores: " + asignacion.getContenedorID());
        System.out.println("Mensaje: " + asignacion.getNotificacion());
        System.out.println("═══════════════════════════════════════════\n");
    }
    
    /**
     * Obtiene una asignación por ID
     */
    public AsignacionDTO getAsignacionById(String asignacionID) {
        return asignaciones.get(asignacionID);
    }
    
    /**
     * Obtiene todas las asignaciones de una planta
     */
    public List<AsignacionDTO> getAsignacionesByPlanta(String plantaID) {
        List<AsignacionDTO> resultado = new ArrayList<>();
        for (AsignacionDTO asignacion : asignaciones.values()) {
            if (asignacion.getPlantaID().equals(plantaID)) {
                resultado.add(asignacion);
            }
        }
        return resultado;
    }
    
    /**
     * Obtiene todas las asignaciones de una fecha
     */
    public List<AsignacionDTO> getAsignacionesByFecha(LocalDate fecha) {
        List<AsignacionDTO> resultado = new ArrayList<>();
        for (AsignacionDTO asignacion : asignaciones.values()) {
            if (asignacion.getFecha().equals(fecha)) {
                resultado.add(asignacion);
            }
        }
        return resultado;
    }
    
    /**
     * Obtiene todas las asignaciones
     */
    public List<AsignacionDTO> getAllAsignaciones() {
        return new ArrayList<>(asignaciones.values());
    }
}
